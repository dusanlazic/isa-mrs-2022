import { useState, useEffect } from "react";
import { get } from "../../../adapters/xhr";
import { Calendar, DateRange } from "react-date-range";

const monthStrings = ['JANUARY', 'FEBRUARY', 'MARCH', 'APRIL', 'MAY', 'JUNE',
                      'JULY', 'AUGUST', 'SEPTEMBER', 'OCTOBER', 'NOVEMBER', 'DECEMBER'];
                      
let queriedMonths = [];
let unavailableDates = [];

const ReservationDatePicker = ({data, type, selectionRange, setSelectionRange }) => {
  const [, update] = useState({});

  useEffect(() => {
    queriedMonths = [];
    getUnavailableDates((new Date()).getFullYear(), monthStrings[(new Date()).getMonth()]);
    getUnavailableDates(
      ((new Date()).getMonth() + 1) % 12 < (new Date()).getMonth() ? (new Date()).getFullYear() + 1 : (new Date()).getFullYear(),
      monthStrings[((new Date()).getMonth() + 1) % 12]);
  }, []);

  const handleShownDateChange = (x) => {
    getUnavailableDates(x.getFullYear(), monthStrings[x.getMonth()]);
    getUnavailableDates(
      (x.getMonth() + 1) % 12 < x.getMonth() ? x.getFullYear() + 1 : x.getFullYear(),
      monthStrings[(x.getMonth() + 1) % 12]);
  }

  const getUnavailableDates = (year, month) => {
    const monthYear = month + year.toString();
    if (queriedMonths.includes(monthYear)) return;
    queriedMonths.push(monthYear);

    get(`/api/ads/${data.id}/unavailable-dates?year=${year}&month=${month}`)
    .then(response => {
      // unavailableDates = unavailableDates.concat(response.data.map(date => new Date(date)));
      for (let d of response.data) {
        unavailableDates.push(new Date(d));
      }
      if (month === monthStrings[(new Date()).getMonth()] && year === (new Date()).getFullYear())
        update({});
    })
  }

  return ( 
    <div>
      { (type === 'resort' || type === 'boat') &&
        <div className="h-min w-min">
          <DateRange
          ranges={[selectionRange]}
          showSelectionPreview={true}
          moveRangeOnFirstSelection={false}
          onChange={item => setSelectionRange(item.selection)}
          minDate={new Date()}
          editableDateInputs={false}
          disabledDates={unavailableDates}
          weekStartsOn={1}
          fixedHeight={true}
          onShownDateChange={(x) => handleShownDateChange(x)}
          direction="horizontal"
          rangeColors={['#0e7490', '#0e7490', '#0e7490']}
          className="h-min w-min border rounded-lg overflow-hidden"
        />
        </div>
      }
      { type === 'adventure' &&
        <Calendar/>
      }
    </div>
   );
}
 
export default ReservationDatePicker;