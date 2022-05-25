import { useState } from "react";
import { del } from "../../adapters/xhr";
import DeletionModal from "../modals/DeletionModal";
import MessageModal from "../modals/MessageModal";

const RemovalRequestForm = ({id}) => {
  const [showForm, setShowForm] = useState(false);
  const [reason, setReason] = useState('');
	const [showModal, setShowModal] = useState(false);
  const [showMessageModal, setShowMessageModal] = useState(false);
  const [messageModalText, setMessageModalText] = useState('');


  const openModal = (event) => {
    if (reason !== '') {
      event.preventDefault();
      setShowModal(true);
    }
  }
  
  const submitForm = () => {
    del('/api/account', {
      "explanation": reason,
    })
    .then((response) => {
      console.log(response.data);
      setMessageModalText('Removal request sent successfully!')
      setShowMessageModal(true);
    })
    .catch(err => {
      console.log(err.response);
      setMessageModalText('Something went wrong. Maybe you\'ve already sent a removal request.');
      setShowMessageModal(true);
    })
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
      <form className="block" onSubmit={openModal}>

        <div>
          <textarea placeholder="I would like for my account to be removed because..."
                    rows={4} required="required" aria-required="true" onChange={event => setReason(event.target.value)} value={reason}
                    className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-sm sm:text-base py-1
                    focus:outline-none focus:border-gray-500 w-full caret-gray-700 resize-none
                    hidden-scrollbar"/>
        </div>

        <div className="flex justify-end mt-4">
          <button onClick={event => openModal(event)}
          className="bg-red-500 hover:bg-red-600 active:bg-red-700 drop-shadow-md modal-button
          text-white rounded-lg h-min w-full md:w-auto px-4 py-2 lg:py-1.5 text-sm lg:text-base">
            Send request
          </button>
        </div>


      </form>}

      {showModal && <DeletionModal  closeFunction = {() => setShowModal(false)}
                                    deleteFunction = {() => submitForm()}
                                    text = {`Are you sure you want to request the permanent removal of your account? This action cannot be undone.`}
        	/>}
      {showMessageModal && <MessageModal  closeFunction = {() => setShowMessageModal(false)}
                                    text = { messageModalText }
        	/>}
    </div>
   );
}
 
export default RemovalRequestForm;