import { useState } from "react";


const RemovalRequestForm = ({id}) => {
  const [showForm, setShowForm] = useState(false);
  const [reason, setReason] = useState('');

  const submitForm = (event) => {
    event.preventDefault();
    
  }

  return ( 
    <div className="block pt-2 text-left w-full">
      <h1 className="text-2xl text-left text-gray-400 font-sans font-light mb-2">Remove account</h1>
      {!showForm &&
      <button className="bg-red-500 hover:bg-red-600 active:bg-red-700 drop-shadow-md
      text-white rounded-lg h-min px-4 py-2 lg:py-1.5 text-sm lg:text-base" onClick={() => setShowForm(true)}>
        Request account removal
      </button>}
      
      {showForm &&
      <form className="block">

        <div>
          <textarea placeholder="I would like for my account to be removed because..."
                    rows={4} required onChange={event => setReason(event.target.value)} value={reason}
                    className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-sm sm:text-base py-1
                    focus:outline-none focus:border-gray-500 w-full caret-gray-700 resize-none
                    hidden-scrollbar"/>
        </div>

        <div className="flex justify-end mt-4">
          <button onClick={event => submitForm(event)}
          className="bg-red-500 hover:bg-red-600 active:bg-red-700 drop-shadow-md modal-button
          text-white rounded-lg h-min w-full md:w-auto px-4 py-2 lg:py-1.5 text-sm lg:text-base">
            Send request
          </button>
        </div>


      </form>}
    </div>
   );
}
 
export default RemovalRequestForm;