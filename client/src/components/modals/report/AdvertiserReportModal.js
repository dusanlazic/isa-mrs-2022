import { useState } from "react";
import { post } from "../../../adapters/xhr";
import { Icon } from "@iconify/react";

import MessageModal from "../MessageModal";
import { Combobox } from "@headlessui/react";

const AdvertiserReviewModal = ({ data, close }) => {
  const [customerWasLate, setCustomerWasLate] = useState(false);
  const [penaltyRequested, setPenaltyRequested] = useState(false);
  const [hoveredStars, setHoveredStars] = useState(-1);
  const [reviewText, setReviewText] = useState('');
  const [errorMsg, setErrorMsg] = useState('');

  const [showMessageModal, setShowMessageModal] = useState(false);
  const [messageModalText, setMessageModalText] = useState('');

  const tryReport = () => {
    if (reviewText.trim().length === 0)
      setErrorMsg('Review text cannot be empty.');
    else if (reviewText.trim().length > 100)
      setErrorMsg('Maximum length surpassed.');
    else sendReport();
  }

  const sendReport = () => {
    console.log(customerWasLate)
    console.log(penaltyRequested)
    post(`/api/reservations/${data.id}/report`,
      {
        customerWasLate: customerWasLate,
        penaltyRequested: penaltyRequested,
        comment: reviewText.trim()
      })
      .then(response => {
        setMessageModalText("Review successfully created.");
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
        <h1 className="text-xl mb-2">Make a report</h1>

        <div className="flex mt-2">
          <input type="checkbox" className="my-auto w-10 h-4 accent-cyan-700"
            onChange={(e) => setCustomerWasLate(e.target.value === "on")} id="wasLate" />
          <label for="wasLate"> Customer was late</label>

          <input type="checkbox" className="my-auto w-10 h-4 accent-cyan-700"
            onChange={(e) => setPenaltyRequested(e.target.value === "on")} id="penalty" />
          <label for="penalty"> Request penalty</label>
        </div>

        <div className="w-full mt-2">
          <textarea className="w-full border border-solid border-slate-200 resize-none h-72 md:h-56
          text-lg text-thin text-gray-700 p-2 leading-tight rounded-md focus:outline-none focus:border-slate-400"
            value={reviewText} onChange={(e) => { setErrorMsg(''); setReviewText(e.target.value); }}
            placeholder={`Your report on ${data.customer.firstName} ${data.customer.lastName}'s reservation of ${data.advertisement.title}.`}></textarea>
          <div className={`flex justify-end -mt-2 ${reviewText.length > 100 ? 'text-red-600' : 'text-slate-500'}`}>
            {reviewText.length}/100
          </div>
        </div>

        <div className="flex justify-center md:justify-end mt-6">
          <div className="block justify-end">
            <button onClick={tryReport} id="tooltip-target-1"
              className={`h-9px-8 px-2 lg:px-10 py-2 
            text-white font-display font-bold shadow-sm focus:outline-none rounded-xl 
            ${reviewText.length < 1 ? 'bg-gray-600 cursor-default' : 'bg-cyan-700 hover:bg-cyan-800 active:bg-cyan-900'}`}>
              Submit report
            </button>
            <div v-if="errorMsg && !selectedStars" className="text-xs h-2 text-gray-400 text-center">{errorMsg}</div>
          </div>
        </div>

        {showMessageModal &&
          <MessageModal okayFunction={() => window.location.reload()}
            closeFunction={() => setShowMessageModal(false)} text={messageModalText}
          />}

      </div>
    </div>
  );
}

export default AdvertiserReviewModal;