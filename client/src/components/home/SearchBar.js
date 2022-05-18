import { Icon } from '@iconify/react';
import { useState } from 'react';

const SearchBar = () => {
  const [selectedType, setSelectedType] = useState('resorts');
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [guests, setGuests] = useState(1);

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

  return ( 
    <div className="w-11/12 md:w-auto h-20
    bg-white mx-auto mb-4 rounded-xl shadow-sm text-left">
      <div className="flex justify-between p-4">

        {/* type of entity */}
        <div>
          <div className='dropdown dropdown-end'>
            <label tabIndex="0" className='btn w-full' id="dropdown-btn-search" onClick={() => handleDropdown()}>
              <Icon className="h-10 w-10 p-2 text-baby-green bg-slate-600 rounded-md"
              icon={getIconBasedOnType()} inline={true} />
            </label>
            <ul tabIndex="0" className="dropdown-content menu p-2 shadow bg-baby-green text-left rounded-box w-40">
              <li onClick={() => setSelectedType('resorts')}>
                <div className='flex text-lg rounded-lg hover:bg-gray-500 hover:bg-opacity-10 px-1'>
                  <Icon className='w-6 h-6 text-gray-600' icon="tabler:window" inline={true} />
                  <span className='ml-1.5 pb-1'>resort</span>
                </div>
              </li>

              <li onClick={() => setSelectedType('boats')}>
                <div className='flex text-lg rounded-lg hover:bg-gray-500 hover:bg-opacity-10 px-1'>
                  <Icon className='w-6 h-6 text-gray-600' icon="tabler:sailboat" inline={true} />
                  <span className='ml-1.5 pb-1'>boat</span>
                </div>
              </li>

              <li onClick={() => setSelectedType('adventures')}>
                <div className='flex text-lg rounded-lg hover:bg-gray-500 hover:bg-opacity-10 px-1'>
                  <Icon className='w-6 h-6 text-gray-600' icon="tabler:fish" inline={true} />
                  <span className='ml-1.5 pb-1'>adventure</span>
                </div>
              </li>

            </ul>

          </div>
        </div>
        
        {/* location */}
        <div className="w-48 shrink-0">
          <div className="font-sans font-medium text-slate-600 text-sm">Location</div>
          <input type="text" name="" id="" placeholder="Where are you going?"
          className="outline-none"/>
        </div>

        <div className="flex w-full gap-x-4 mr-4">
          <div className='flex-grow'>
            <div className="font-sans font-medium text-slate-600 text-sm">
              Check In
            </div>
            <input type="date" className="outline-none w-28"/>
          </div>
          
          <div className='flex-grow'>
            <div className="font-sans font-medium text-slate-600 text-sm">
              Check Out
            </div>
            <input type="date" className='outline-none w-28'/>
          </div>

          <div>
            <div className="font-sans font-medium text-slate-600 text-sm">
              Guests
            </div>
            <input type="number" value={guests} onChange={event => setGuests(event.target.value)}
             className="w-12 outline-none border-b border-slate-400"/>
          </div>

        </div>

        {/* button */}
        <div className='shrink-0 p-1'>
          <button className='h-10 w-10 text-baby-green bg-slate-600 rounded-md text-center'>
            <Icon icon='tabler:search' className='mx-auto'/>
          </button>
        </div>

      </div>
    </div>
   );
}
 
export default SearchBar;