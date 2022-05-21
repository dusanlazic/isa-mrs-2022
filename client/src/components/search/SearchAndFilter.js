import { useState } from 'react';
import { Icon } from '@iconify/react';
import DatePicker from "react-datepicker";
import { isDOMComponent } from 'react-dom/test-utils';

const SearchAndFilter = ({whereProp, entityProp, guestsProp, startDateProp, endDateProp, searchResults}) => {
  
  const [where, setWhere] = useState(whereProp);
  const [startDate, setStartDate] = useState(startDateProp);
  const [endDate, setEndDate] = useState(endDateProp);
  const [guests, setGuests] = useState(guestsProp);

  const [selectedEntity, setSelectedEntity] = useState(entityProp);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);


  const handleDropdown = () => {
    if (isDropdownOpen && document.activeElement === document.getElementById('dropdown-btn-search')) {
      setIsDropdownOpen(false);
      removeFocusFromDropdown();
    } else {
      setIsDropdownOpen(true);
    }
  }

  const removeFocusFromDropdown = () => {
    document.getElementById('dropdown-btn-search').blur();
  }

  const getIconBasedOnType = () => {
    if (selectedEntity.includes('resorts'))
      return 'tabler:window';
    else if (selectedEntity.includes('boats'))
      return 'tabler:sailboat';
    else if (selectedEntity.includes('adventures'))
      return 'tabler:fish';
  }

  return ( 
    <div className="w-full text-left">
      <div className="bg-white rounded-xl shadow-sm p-6">
      <h1 className="text-2xl text-left text-gray-400 font-sans font-light leading-4">Parameters</h1>

        {/* first row */}
        <div className='flex flex-wrap gap-x-2 gap-y-2 mt-3'>
          {/* entity and location search */}
          <div className="flex w-min border border-slate-200 rounded-lg divide-x">
            {/* type of entity */}
            <div className='flex flex-col h-10'>
              <div className='dropdown my-auto h-8' id='12341234123'>
                <button tabIndex="0" className='px-1 my-auto' id="dropdown-btn-search"
                onClick={() => handleDropdown()} onBlur={e => setIsDropdownOpen(false)}>
                  <Icon className="h-8 w-8 p-1.5 text-gray-50 bg-slate-600 rounded-md transition ease-in-out hover:scale-105"
                  icon={getIconBasedOnType()} inline={true} />
                </button>
                <ul tabIndex="0" className="dropdown-content menu p-2 shadow bg-slate-600 text-left rounded-box w-40">
                  <li onClick={() => setSelectedEntity('resorts')}>
                    <div className='flex text-lg rounded-lg hover:bg-slate-700 text-gray-50 hover:bg-opacity-75 px-1'>
                      <Icon className='w-6 h-6 ' icon="tabler:window" inline={true} />
                      <span className='ml-1.5 pb-1'>resort</span>
                    </div>
                  </li>

                  <li onClick={() => setSelectedEntity('boats')}>
                    <div className='flex text-lg rounded-lg hover:bg-slate-700 text-gray-50 hover:bg-opacity-75 px-1'>
                      <Icon className='w-6 h-6 ' icon="tabler:sailboat" inline={true} />
                      <span className='ml-1.5 pb-1'>boat</span>
                    </div>
                  </li>

                  <li onClick={() => setSelectedEntity('adventures')}>
                    <div className='flex text-lg rounded-lg hover:bg-slate-700 text-gray-50 hover:bg-opacity-75 px-1'>
                      <Icon className='w-6 h-6 ' icon="tabler:fish" inline={true} />
                      <span className='ml-1.5 pb-1'>adventure</span>
                    </div>
                  </li>

                </ul>

              </div>
            </div>

            <div className="pl-2 pr-3 my-auto">
              <input type="text" name="" id="" placeholder="Where are you going?"
              className="outline-none w-28 sm:w-80 h-8 border-b border-dashed bg-transparent"
              value={where} onChange={event => setWhere(event.target.value)}/>
            </div>

          </div>

          <div className='relative my-auto h-10'>
            {guests &&
            <div className='absolute top-0.5 text-xs w-20 text-center font-thin text-slate-400 z-10'>guests:</div>}
            <input
              onChange={(e) => setGuests(e.target.value)}
              placeholder="Guests"
              className={`w-20 outline-none text-center rounded-lg placeholder-slate-400
              text-slate-600 focus:text-slate-900 h-10 px-2 border border-slate-200
              ${guests ? 'pt-2 font-medium' : ''}`}
            />
          </div>

          {/* dates */}
          <div className='relative my-auto'>
            {startDate &&
            <div className='absolute top-0.5 text-xs w-28 text-center font-thin text-slate-400 z-10'>from:</div>}
            <DatePicker
              dateFormat="dd-MM-yyyy"
              selected={startDate}
              onChange={(date) => setStartDate(date)}
              placeholderText="Start date"
              className={`w-28 outline-none text-center rounded-lg placeholder-slate-400
              text-slate-600 focus:text-slate-900 h-10 px-2 border border-slate-200
              ${startDate ? 'pt-2 font-medium' : ''}`}
            />
          </div>
          
          <div className='relative my-auto h-10'>
           {endDate &&
            <div className='absolute top-0.5 text-xs w-28 text-center font-thin text-slate-400 z-10'>to:</div>}
            <DatePicker
              dateFormat="dd-MM-yyyy"
              selected={endDate}
              onChange={(date) => setEndDate(date)}
              placeholderText="End date"
              className={`w-28 outline-none text-center rounded-lg placeholder-slate-400
              text-slate-600 focus:text-slate-900 h-10 px-2 border border-slate-200
              ${endDate ? 'pt-2 font-medium' : ''}`}
            />
          </div>
          
          
        </div>
        


        {/* search results number */}
        <div className='flex flex-wrap w-full justify-between mt-4'>

          <div className='block'>
            <div className='text-xs text-gray-400 tracking-wide'>Searching results:</div>
              <div className='text-xl'>
                <span className='text-slate-700 font-medium'>{searchResults} Result{searchResults === 1 ? '' : 's' } </span>
                <span className='text-gray-500'>{where ? 'in ' + where : ''}</span>
              </div>
            </div>

          <div className='flex self-end gap-x-3 mt-2 sm:mt-0'>
            <button className='border-white text-raisin-black font-medium rounded-lg text-sm sm:text-base
            px-3 sm:px-6 py-1 bg-silver-accent hover:bg-slate-300'>Clear all</button>
            <button className='border-white text-raisin-black font-medium rounded-lg text-sm sm:text-base
            px-3 sm:px-6 py-1 bg-silver-accent hover:bg-slate-300'>Apply filter</button>
          </div>
        </div>
      </div>
    </div>
   );
}
 
export default SearchAndFilter;