import { useState } from "react";
import { Link } from "react-router-dom";
import { Icon } from "@iconify/react";

const ClientReview = ({review}) => {
  console.log(review)
  let [showMore, setShowMore] = useState(false);

  const getPlaceholderImage = () => {
    if (review.advertisement.advertisementType === 'resort') return '/images/property-placeholder.jpg'
    if (review.advertisement.advertisementType === 'boat') return '/images/boat-placeholder.jpg'
    if (review.advertisement.advertisementType === 'adventure') return '/images/fish-placeholder.jpg'
  }

  return ( 
    <div className="block rounded-lg">
      <div className="flex">
        <Link to={`/${review.advertisement.advertisementType}/${review.advertisement.id}`}>
          <img src={review.advertisement.photo ? `/api/${review.advertisement.photo.uri}` : getPlaceholderImage()}
          alt="" className="flex-none w-20 h-14 object-cover rounded-lg" />
        </Link>

        <div className="flex flex-col ml-2 justify-between">
          <div>
            <Link to={`/${review.advertisement.advertisementType}/${review.advertisement.id}`}>
              <h1 className="text-lg text-slate-700 leading-4 tracking-tight my-auto text-left">
                  {review.advertisement.title}
              </h1>
            </Link>
            
            <Link to={`/advertiser/${review.advertiser.id}`}>
              <h4 className="text-sm text-slate-500 leading-4 tracking-tight my-auto text-left">
                  {review.advertiser.firstName} {review.advertiser.lastName}
              </h4>
            </Link>
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
 
export default ClientReview;