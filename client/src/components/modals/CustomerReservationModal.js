import { useState, useEffect } from "react";
import { Checkbox } from "react-daisyui";
import ReservationDatePicker from "./util/ReservationDatePicker";

const CustomerReservationModal = ({data, close}) => {
  const [advertisementType, setAdvertisementType] = useState("");
  const [attendees, setAttendees] = useState(1);
  const [options, setOptions] = useState({});
  const [selectionRange, setSelectionRange] = useState(
    {
      startDate: new Date(),
      endDate: new Date(),
      key: 'selection'
    }
  )

  useEffect(() => {
    decideType();
    fillOptions();
  }, []);

  const fillOptions = () => {
    let initOptions = {};
    data.options.sort(compare);
    for (const option of data.options) {
      if (option.maxCount > 1)
        initOptions[option.id] = 0;
      else
        initOptions[option.id] = false;
    }
    console.log(initOptions);
    setOptions(initOptions);
  }

  const decideType = () => {
    if (window.location.href.includes('resort')) {
      setAdvertisementType('resort');
    }
    else if (window.location.href.includes('boat')) {
      setAdvertisementType('boat');
    }
    else {
      setAdvertisementType('adventure');
    }
  }

  const closeModal = (e) => {
    if (e.target === e.currentTarget) {
      close();
    }
  }

  const handleSetAttendees = (value) => {
    if (value.length === 0) {
      setAttendees(value);
      return;
    }
    value < 1 ? setAttendees(1) : setAttendees(value);
    value > data.capacity ? setAttendees(data.capacity) : setAttendees(value);
  }

  const handleSetOption = (option, value) => {
    let newOptions = {...options};
    if (value.length === 0) {
      newOptions[option.id] = value;
    }
    else {
      if (value === "on") newOptions[option.id] = !newOptions[option.id]
      else {
        value < 0 ? newOptions[option.id] = 0 : 
        value > option.maxCount ? newOptions[option.id] = option.maxCount : newOptions[option.id] = value;
      }
    }
    setOptions(newOptions);
  }

  return ( 
    <div onClick={closeModal} className="fixed top-0 left-0 z-50 w-full min-h-screen h-screen text-center
    flex items-center justify-center bg-gray-900 bg-opacity-70 font-mono transition-opacity text-base">
      <div className="relative flex flex-col w-180 h-140 bg-white rounded-xl mx-auto overflow-hidden p-9">
        <h1 className="text-xl mb-5 font-display">Make a reservation</h1>


        <div className="flex gap-x-6">
          <ReservationDatePicker data={data} type={advertisementType} 
          selectionRange={selectionRange} setSelectionRange={setSelectionRange}/>
          

          <div className="block w-full text-left">
            {/* attendees */}
            <div>
              <label className="text-xs text-slate-500">Number of attendees (max {data.capacity}):</label>
              <input type="number" name="attendees" placeholder="Attendees" value={attendees}
                  onChange={(e) => handleSetAttendees(e.target.value)}
                  className="block rounded-lg px-3 border text-gray-700 border-gray-300 py-1
                  focus:outline-none focus:border-gray-500  caret-gray-700"/>
            </div>

            {/* options */}
            <div className="text-xs text-slate-500 mt-4 mb-2">Options:</div>
            <div className="flex flex-col divide-y font-display h-68 overflow-auto">
              {data.options.map(option => 
                <div key={option.id} className="py-1">
                  {option.maxCount === 1 &&
                    <div className="flex gap-x-2">
                      <Checkbox className="my-auto w-10 h-4 accent-cyan-700" onChange={(e) => handleSetOption(option, e.target.value)}
                      checked={Object.keys(options).length > 0 ? options[option.id] : ""}/>

                      <div className="block">
                        <p>{option.name}</p>
                        <div className="text-xs font-display text-slate-700">{option.description}</div>
                      </div>
                    </div>
                  }
                  {option.maxCount > 1 &&
                    <div className="flex w-full gap-x-2">
                      <input type="number" placeholder={option.name}
                      value={Object.keys(options).length > 0 ? options[option.id] : ""}
                        onChange={(e) => handleSetOption(option, e.target.value)}
                        className={`block rounded-lg px-2.5 text-center border text-gray-700 border-gray-300 py-1
                        focus:outline-none focus:border-gray-500 w-10 caret-gray-700 shrink h-10
                        ${options[option.id] > 0 ? 'border-cyan-700 border-2' : ''}`}/>

                        <div className="block">
                          <p className="my-auto break-all">{option.name} (max {option.maxCount})</p>
                          <div className={`text-xs font-display text-slate-700
                          ${option.description.length === 0 ? 'hidden' : 'block'}`}>{option.description}</div>
                        </div>
                    </div>
                  }
                </div>
                )}
            </div>

            <div>
              <button className="rounded-xl shadow-sm px-6 py-2 text-white font-bold 
              bg-cyan-700 hover:bg-cyan-800 active:bg-cyan-900 focus:outline-none
              w-full mt-4">
                Make a reservation
              </button>
            </div>

          </div>
        </div>
        
      </div>
    </div>
   );
}

function compare( a, b ) {
  if ( a.maxCount === 1){
    return -1;
  }
  return 1;
}
 
export default CustomerReservationModal;