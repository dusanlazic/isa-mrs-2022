import { useState, useEffect } from "react";
import { get, put } from "../adapters/xhr";
import { useNavigate } from 'react-router-dom';
import { Icon } from "@iconify/react";

const SetCommissionRatePage = () => {
  const [value, setValue] = useState(null);
  
  const navigate = useNavigate();
  const [errors, setErrors] = useState(null);

  useEffect(() => {
    get(`/api/admin/system/commission-rate`)
    .then(response => {
      setValue(response.data.value);
    })
    .catch(error => {
      navigate('/');
    });
  }, [])

  const save = () => {
    put("/api/admin/system/commission-rate", {
      value: value,
    })
    .then((response) => {
      alert(response.data.message);
    })
    .catch((error) => {
      setErrors(error.response.data.errors);
      alert(error.response.data.message);
    });
  }

  if (value == null) {
    return
  }

  return ( 
    <div className="block min-h-screen p-32 px-8 sm:px-20 md:px-52 lg:px-60 xl:px-96 w-full font-display">
      <div className="flex flex-col divide-y divide-dashed
       h-full border-0.5 rounded-lg gap-y-4">
        <div className="block w-full">
          <h1 className="text-2xl text-left text-gray-400 font-sans">Configure commission rates</h1>

          {/* Commission rate */}
          <h2 className="flex text-xl text-left text-gray-800 font-sans mt-6 pt-6">
            <Icon className="mr-2" icon="tabler:cash" inline={true} fontSize={30} />
            <span>Commission rate</span>
          </h2>

          <div className="grid grid-cols-6 mt-2 gap-x-6">
            <div className="block col-span-2 text-left">
              <label className="text-xs">Percentage of advertisers' revenue that goes to owners</label>
              <input type="number"
              min="0"
              max="100"
              value={value * 100}
              onChange={(event) => {setValue(event.target.value / 100)}}
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                <p className="text-xs text-red-500 my-0 tracking-wide">{errors === null ? '' : errors.value}</p>
              </div>
            </div>
          </div>

          <div className="grid grid-cols-6 mt-2">
            <button className="bg-teal-600 hover:bg-teal-700 active:bg-teal-800 drop-shadow-md
              text-white rounded-lg py-2.5 lg:py-2 text-sm lg:text-base mb-1.5"
              onClick={save}>
                Save configuration
            </button>
          </div>
        </div>
      </div>

    </div>
   );
}
 
export default SetCommissionRatePage;