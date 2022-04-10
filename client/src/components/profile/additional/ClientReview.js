import { useState } from "react";


const ClientReview = ({review}) => {
  let [showMore, setShowMore] = useState(false);

  return ( 
    <div className="block rounded-lg">
      <div className="flex">
        <img src={review.advertisement.image} alt="" className="flex-none w-20 h-14 object-cover rounded-lg" />

        <div className="block ml-2">
          <h1 className="text-xl text-gray-700 tracking-tight my-auto text-left">
              {review.advertisement.name}
          </h1>
          <div className="flex text-lg -mt-2">
            {[0,1,2,3,4].map((id) =>
              <span key={id}>
                {id < review.rating  ? <div className="text-yellow-500">&#9733;</div> 
                : <div className="text-gray-300">&#9733;</div>}
              </span>
            )}
          </div>
        </div>
      </div>

      {/* Text */}
      <div className="block w-full text-left mt-1.5">
        <p className="text-gray-500 text-justify pr-2 ">
          {showMore ? review.text : review.text.substring(0, 200)}{!showMore && review.text.length >= 200 ? '...' : ''}
        </p>

        {review.text.length >= 200 && 
        <button onClick={() => setShowMore(!showMore)} className="text-sm text-gray-400 hover:text-gray-600">
          {showMore ? 'Show less' : 'Show more'}
        </button>
        }
      </div>
    </div>
   );
}
 
export default ClientReview;