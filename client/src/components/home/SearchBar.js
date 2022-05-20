import { Icon } from '@iconify/react';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import moment from 'moment';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";


const SearchBar = () => {
  const [where, setWhere] = useState('');
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [selectedEntity, setSelectedEntity] = useState('resorts');
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [guests, setGuests] = useState('');

  const handleCalendarClose = () => console.log(startDate);
  const handleCalendarOpen = () => console.log(endDate);

  const handleDropdown = () => {
    if (isDropdownOpen && document.activeElement === document.getElementById('dropdown-btn-search')) {
      console.log(document.activeElement)
      setIsDropdownOpen(false);
      removeFocusFromDropdown();
    } else {
      setIsDropdownOpen(true);
    }
  }

  const removeFocusFromDropdown = () => {
    document.getElementById('dropdown-btn-search').blur();
  }

  const getQueryString = () => {
    let query = '';
    if (where.trim() !== '' || guests.trim() !== '' || startDate || endDate) {
      query += '?';
      query += where.trim() !== '' ? 'where=' + where.trim() : '';
      query += guests.trim() !== '' ? `${query !== '?' ? '&' : ''}guests=` + guests.trim() : '';
      
      if (startDate) {
        const start = moment(startDate).format('DD-MM-yyyy');
        query += start ? `${query !== '?' ? '&' : ''}startDate=` + start : '';
      }
      if (endDate) {
        const end = moment(endDate).format('DD-MM-yyyy');
        query += end ? `${query !== '?' ? '&' : ''}endDate=` + end : '';
      }
    }
    return query;
  }

  const getIconBasedOnType = () => {
    if (selectedEntity.includes('resorts'))
      return 'tabler:window';
    else if (selectedEntity.includes('boats'))
      return 'tabler:sailboat';
    else if (selectedEntity.includes('adventures'))
      return 'tabler:fish';
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
            <label tabIndex="0" className='btn px-0 pr-4 pl-4' id="dropdown-btn-search" onClick={() => handleDropdown()}>
              <Icon className="h-10 w-10 p-2.5 text-gray-50 bg-slate-600 rounded-md transition ease-in-out hover:scale-105"
              icon={getIconBasedOnType()} inline={true} />
            </label>
            <ul tabIndex="0" className="dropdown-content menu p-2 shadow bg-slate-600 text-left rounded-box w-40">
              <li onClick={() => {setSelectedEntity('resorts'); handleDropdown();}}>
                <div className='flex text-lg rounded-lg hover:bg-slate-700 text-gray-50 hover:bg-opacity-75 px-1'>
                  <Icon className='w-6 h-6 ' icon="tabler:window" inline={true} />
                  <span className='ml-1.5 pb-1'>resort</span>
                </div>
              </li>

              <li onClick={() => {setSelectedEntity('boats'); handleDropdown();}}>
                <div className='flex text-lg rounded-lg hover:bg-slate-700 text-gray-50 hover:bg-opacity-75 px-1'>
                  <Icon className='w-6 h-6 ' icon="tabler:sailboat" inline={true} />
                  <span className='ml-1.5 pb-1'>boat</span>
                </div>
              </li>

              <li onClick={() => {setSelectedEntity('adventures'); handleDropdown();}}>
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
          className="outline-none" value={where} onChange={event => setWhere(event.target.value)}/>
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
          <Link to={`/ads/${selectedEntity}` + getQueryString()}>
          <button className='h-10 w-10 text-gray-50 bg-slate-600 rounded-md text-center transition ease-in-out hover:scale-105'>
            <Icon icon='tabler:search' className='mx-auto'/>
          </button>
          </Link>
        </div>

      </div>
    </div>
   );
}
 
export default SearchBar;