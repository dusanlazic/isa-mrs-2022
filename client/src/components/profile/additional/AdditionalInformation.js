import { useState } from 'react'

const AdditionalInformation = ({options}) => {
  
  const [selectedOption, setSelectedOption] = useState(options[0]);
 
  return ( 
    <div>
      {/* Mini navigation */}
      <div className="flex flex-wrap justify-around md:justify-start gap-x-2 sm:gap-x-0 w-full mt-4 rounded-lg bg-gray-100 px-1 py-1 font-sans tracking-wider">
      {options.map((option) => 
        <button className={`my-auto flex-grow md:flex-grow-0 px-6 py-2 text-gray-400 active:bg-white font-semibold
        hover:text-gray-800 cursor-pointer rounded-lg sm:rounded-none
        ${options.indexOf(option) === 0 ? 'sm:rounded-l-lg' : ''}
        ${options.indexOf(option) === options.length - 1 ? 'sm:rounded-r-lg' : ''}
        ${option === selectedOption ? 'bg-white text-gray-800' : ''}
        `} onClick={() => setSelectedOption(option)}
        key={options.indexOf(option)}>
          {option.title}
        </button>
        )}
      </div>


      {/* Content */}
      <div className=' w-full mt-6 rounded-lg border border-gray-100
      shadow-sm p-4 md:p-6 gap-y-4'>
        {selectedOption.component}
      </div>
    </div>
   );
}
 
export default AdditionalInformation;