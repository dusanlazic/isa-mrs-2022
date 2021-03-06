import { useState } from "react";
import { post } from "../../../adapters/xhr";

import MessageModal from "../MessageModal";

const CustomerComplaintModal = ({data, close}) => {
  const [complaintText, setComplaintText] = useState('');
  const [errorMsg, setErrorMsg] = useState('');

  const [showMessageModal, setShowMessageModal] = useState(false);
  const [messageModalText, setMessageModalText] = useState('');

  const tryComplaint = () => {
    if (complaintText.trim().length === 0)
      setErrorMsg('Enter a message.');
    else sendComplaint();
  }

  const sendComplaint = () => {
    post(`/api/ads/${data.advertisement.id}/complaints`,
    {
      comment: complaintText.trim()
    })
    .then(response => {
      setMessageModalText("Complaint successfully sent.");
      setShowMessageModal(true);
    })
    .catch(error => {
      setMessageModalText(error.response.data.message);
      setShowMessageModal(true);
    })
  }

  const closeModal = (e) => {
    if (e.target === e.currentTarget) {
      close();
    }
  }

  return ( 
    <div onClick={event => closeModal(event)} className="fixed top-0 left-0 z-30 w-full min-h-screen h-screen text-center
    flex items-center justify-center bg-gray-900 bg-opacity-70 transition-opacity text-base font-display">
      <div className="relative flex flex-col w-180 h-132 bg-white rounded-xl mx-auto overflow-hidden px-9 py-7">
        <h1 className="text-xl mb-2">Write a complaint</h1>

        <div className="w-full mt-2">
          <textarea className="w-full border border-solid border-slate-200 resize-none h-72 md:h-56
          text-lg text-thin text-gray-700 p-2 leading-tight rounded-md focus:outline-none focus:border-slate-400"
          value={complaintText} onChange={(e) => {setErrorMsg(''); setComplaintText(e.target.value);}}
          placeholder={`Your complaint of ${data.advertisement.title}`}></textarea>
          <div className={`flex justify-end -mt-2 text-slate-500`}>
            {complaintText.length}/<span className="font-sans font-medium">∞</span>
          </div>
        </div>

        <div className="flex justify-center md:justify-end mt-6">
          <div className="block justify-end">
            <button onClick={tryComplaint} id="tooltip-target-1"
            className={`h-9px-8 lg:px-10 py-2 bg-cyan-700 hover:bg-cyan-800 active:bg-cyan-900
            text-white font-display font-bold shadow-sm focus:outline-none rounded-xl`}>
              leave review
            </button>
            <div v-if="errorMsg" className="text-xs h-2 text-gray-400 text-center">{ errorMsg }</div>
          </div>
        </div>

        {showMessageModal &&
        <MessageModal okayFunction={() => window.location.reload()}
        closeFunction = {() => setShowMessageModal(false)} text = { messageModalText }
        />}
        
      </div>
    </div>
   );
}
 
export default CustomerComplaintModal;