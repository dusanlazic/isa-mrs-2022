import { Icon } from '@iconify/react';
import { useState } from 'react';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";


const SearchBar = () => {
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [selectedType, setSelectedType] = useState('resorts');
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [guests, setGuests] = useState('');

  const handleCalendarClose = () => console.log(startDate);
  const handleCalendarOpen = () => console.log(endDate);

  const handleDropdown = () => {
    if (isDropdownOpen) {
      setIsDropdownOpen(false);
      removeFocusFromDropdown();
    } else {
      setIsDropdownOpen(true);
    }
  }

  const getIconBasedOnType = () => {
    if (selectedType.includes('resorts'))
      return 'tabler:window';
    else if (selectedType.includes('boats'))
      return 'tabler:sailboat';
    else if (selectedType.includes('adventures'))
      return 'tabler:fish';
  }

  const removeFocusFromDropdown = () => {
    document.getElementById('dropdown-btn-search').blur();
  }

  const handleSetGuests = (guests) => {
    if (guests.length === 0) {
      setGuests(guests);
      return;
    }
    guests < 1 ? setGuests(1) : setGuests(guests);
    
  }

  return ( 
    <div className="hidden sm:block w-auto h-16 lg:h-20
    bg-white mx-auto mb-4 rounded-xl shadow-sm text-left">
      <div className="flex justify-between p-2 lg:p-4">

        {/* type of entity */}
        <div>
          <div className='dropdown sm:dropdown-end'>
            <label tabIndex="0" className='btn w-full' id="dropdown-btn-search" onClick={() => handleDropdown()}>
              <Icon className="h-10 w-10 p-2.5 text-gray-50 bg-slate-600 rounded-md"
              icon={getIconBasedOnType()} inline={true} />
            </label>
            <ul tabIndex="0" className="dropdown-content menu p-2 shadow bg-slate-600 text-left rounded-box w-40">
              <li onClick={() => setSelectedType('resorts')}>
                <div className='flex text-lg rounded-lg hover:bg-slate-700 text-gray-50 hover:bg-opacity-75 px-1'>
                  <Icon className='w-6 h-6 ' icon="tabler:window" inline={true} />
                  <span className='ml-1.5 pb-1'>resort</span>
                </div>
              </li>

              <li onClick={() => setSelectedType('boats')}>
                <div className='flex text-lg rounded-lg hover:bg-slate-700 text-gray-50 hover:bg-opacity-75 px-1'>
                  <Icon className='w-6 h-6 ' icon="tabler:sailboat" inline={true} />
                  <span className='ml-1.5 pb-1'>boat</span>
                </div>
              </li>

              <li onClick={() => setSelectedType('adventures')}>
                <div className='flex text-lg rounded-lg hover:bg-slate-700 text-gray-50 hover:bg-opacity-75 px-1'>
                  <Icon className='w-6 h-6 ' icon="tabler:fish" inline={true} />
                  <span className='ml-1.5 pb-1'>adventure</span>
                </div>
              </li>

            </ul>

          </div>
        </div>
        
        {/* location */}
        <div className="w-24 lg:w-48 shrink-0">
          <div className="font-sans font-medium text-slate-600 text-sm">Location</div>
          <input type="text" name="" id="" placeholder="Where are you going?"
          className="outline-none"/>
        </div>

        <div className="flex w-full gap-x-1 lg:gap-x-4 mr-4">
          <div className='flex-grow'>
            <div className="font-sans font-medium text-slate-600 text-sm">
              Start date
            </div>
            {/* <input className="outline-none border-b border-slate-400" /> */}
            <DatePicker
              dateFormat="dd-MM-yyyy"
              selected={startDate}
              onChange={(date) => setStartDate(date)}
              onCalendarClose={handleCalendarClose}
              onCalendarOpen={handleCalendarOpen}
              placeholderText="Select a date"
              className="w-24 outline-none text-slate-600 focus:text-slate-900"
            />
          </div>
          
          <div className='flex-grow'>
            <div className="font-sans font-medium text-slate-600 text-sm">
              End date
            </div>
            {/* <input className="outline-none border-b border-slate-400 w-24" /> */}
            <DatePicker
              dateFormat="dd-MM-yyyy"
              selected={endDate}
              onChange={(date) => setEndDate(date)}
              onCalendarClose={handleCalendarClose}
              onCalendarOpen={handleCalendarOpen}
              placeholderText="Select a date"
              className="w-24 outline-none text-slate-600 focus:text-slate-900"
            />
        
          </div>

          <div>
            <div className="font-sans font-medium text-slate-600 text-sm">
              Guests
            </div>
            <input type="number" value={guests} onChange={event => handleSetGuests(event.target.value)}
             className="w-12 outline-none border-b border-transparent focus:border-slate-300 " placeholder="Add"/>
          </div>

        </div>

        {/* button */}
        <div className='shrink-0 p-1 pr-4'>
          <button className='h-10 w-10 text-gray-50 bg-slate-600 rounded-md text-center'>
            <Icon icon='tabler:search' className='mx-auto'/>
          </button>
        </div>

      </div>
    </div>
   );
}
 
export default SearchBar;