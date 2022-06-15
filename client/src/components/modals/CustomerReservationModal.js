import { useState } from "react";
import { useEffect } from "react";
import { get } from "../../adapters/xhr";
import { Calendar, DateRange } from "react-date-range";

const monthStrings = ['JANUARY', 'FEBRUARY', 'MARCH', 'APRIL', 'MAY', 'JUNE',
                      'JULY', 'AUGUST', 'SEPTEMBER', 'OCTOBER', 'NOVEMBER', 'DECEMBER'];

let queriedMonths = [];
let unavailableDates = [];


// updating this component is the worst experience of my life
// don't ever use this in your projects
const CustomerReservationModal = ({data, close}) => {
  const [, update] = useState({});
  const [type, setType] = useState(null);
  const [selectionRange, setSelectionRange] = useState(
    {
      startDate: new Date(),
      endDate: new Date(),
      key: 'selection'
    }
  )

  useEffect(() => {
    decideType();
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

  const decideType = () => {
    if (window.location.href.includes('resort')) {
      setType('resort');
    }
    else if (window.location.href.includes('boat')) {
      setType('boat');
    }
    else {
      setType('adventure');
    }
  }

  const closeModal = (e) => {
    if (e.target === e.currentTarget) {
      close();
    }
  }

  return ( 
    <div onClick={closeModal} className="fixed top-0 left-0 z-50 w-full min-h-screen h-screen text-center
    flex items-center justify-center bg-gray-900 bg-opacity-70 font-mono transition-opacity">
      <div className="relative flex flex-col w-180 h-140 bg-white rounded-xl mx-auto overflow-hidden p-9">
        <div className="text-xl mb-6">Make a reservation</div>
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
            className="h-min w-min border rounded-lg overflow-hidden"
          />
          </div>
        }
        { type === 'adventure' &&
          <Calendar/>
        }
        <div className="w-full">

        </div>
      {/* onShownDateChange={handleShownDateChange} */}
      </div>
    </div>
   );
}
 
export default CustomerReservationModal;