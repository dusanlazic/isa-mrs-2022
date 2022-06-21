import { useState } from "react";
import { Link } from "react-router-dom";
import { Icon } from "@iconify/react";

const AdvertisementReview = ({review}) => {
  let [showMore, setShowMore] = useState(false);

  return ( 
    <div className="block rounded-lg">
      <div className="flex">
        <Link to={`/${review.advertisement.advertisementType}/${review.advertisement.id}`}>
          <img src={review.customer.avatar ? `/api/${review.customer.avatar.uri}` : '/images/default.jpg'}
          alt="" className="flex-none w-14 h-14 object-cover rounded-lg" />
        </Link>

        <div className="flex flex-col ml-2">
          <div>
            
            {/* dodati prikaz za mini-profil customera */}
            <h1 className="text-lg text-slate-700 leading-4 tracking-tight my-auto text-left">
                {review.customer.firstName} {review.customer.lastName}
            </h1>

          </div>
          <div className="flex text-lg mt-1">
            {[0,1,2,3,4].map((id) =>
              <span key={id}>
                 <Icon icon="tabler:star" inline={true}
                  className={`my-auto w-4 h-4
                  ${id < review.rating ? 
                    'text-green-700' :
                    'text-gray-400'}`}/>
              </span>
            )}
          </div>
        </div>
      </div>

      {/* Text */}
      <div className="block w-full text-left mt-1.5">
        <p className="text-gray-500 text-justify pr-2 ">
          {showMore ? review.comment : review.comment.substring(0, 200)}{!showMore && review.comment.length >= 200 ? '...' : ''}
        </p>

        {review.comment.length >= 200 && 
        <button onClick={() => setShowMore(!showMore)} className="text-sm text-gray-400 hover:text-gray-600">
          {showMore ? 'Show less' : 'Show more'}
        </button>
        }
      </div>
    </div>
   );
}
 
export default AdvertisementReview;