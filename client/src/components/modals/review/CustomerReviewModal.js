import { useState } from "react";
import { post } from "../../../adapters/xhr";
import { Icon } from "@iconify/react";

import MessageModal from "../MessageModal";

const CustomerReviewModal = ({data, close}) => {
  const [selectedStars, setSelectedStars] = useState(-1);
  const [hoveredStars, setHoveredStars] = useState(-1);
  const [reviewText, setReviewText] = useState('');
  const [errorMsg, setErrorMsg] = useState('');

  const [showMessageModal, setShowMessageModal] = useState(false);
  const [messageModalText, setMessageModalText] = useState('');

  const tryReview = () => {
    if (reviewText.trim().length === 0) 
      setErrorMsg('Review text cannot be empty.');
    else sendReview();
  }

  const sendReview = () => {
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
        <h1 className="text-xl mb-2">Rate and review</h1>

        <div className="flex mt-2">
          { [0, 1, 2, 3, 4].map(n => 
            <span className={`${n !== 0 ? 'pl-0.5' : ''}`} key={n} onClick={() => setSelectedStars(n)} onMouseOver={() => setHoveredStars(n)} onMouseLeave={() => setHoveredStars(-1)}>
              <Icon icon="tabler:star" inline={true}
              className={`my-auto w-5 h-5 cursor-pointer
              ${(hoveredStars > -1 ? hoveredStars : selectedStars) >= n ? 
                'text-green-700  transition duration-300 ease-in-out transform -translate-y-0.5' :
                'text-gray-300'}`}/>
            </span>
          ) }

        </div>

        <div className="w-full mt-2">
          <textarea className="w-full border border-solid border-slate-200 resize-none h-72 md:h-56
          text-lg text-thin text-gray-700 p-2 leading-tight rounded-md focus:outline-none focus:border-slate-400"
          value={reviewText} onChange={(e) => {setErrorMsg(''); setReviewText(e.target.value);}}
          placeholder={`Your review of ${data.advertisement.title}`}></textarea>
        </div>

        <div className="flex justify-center md:justify-end mt-6">
          <div className="block justify-end">
            <button onClick={tryReview} id="tooltip-target-1"
            className={`h-9px-8 lg:px-10 py-2 
            text-white font-display font-bold shadow-sm focus:outline-none rounded-xl 
            ${selectedStars < 0 ? 'bg-gray-600 cursor-default' : 'bg-cyan-700 hover:bg-cyan-800 active:bg-cyan-900 '}`}>
              leave review
            </button>
            <div v-if="errorMsg && !selectedStars" className="text-xs h-2 text-gray-400 text-center">{ errorMsg }</div>
          </div>
        </div>

        {showMessageModal &&
        <MessageModal okayFunction={() => close()} closeFunction = {() => setShowMessageModal(false)} text = { messageModalText }
        />}
        
      </div>
    </div>
   );
}
 
export default CustomerReviewModal;