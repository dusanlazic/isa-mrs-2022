import { useState, useEffect } from "react";
import { get, patch } from "../../adapters/xhr";
import { Icon } from '@iconify/react';
import Moment from 'moment';

const ReviewsPage = () => {
  const [reviews, setReviews] = useState(null);
  
  useEffect(() => {
    get(`/api/admin/reviews/`)
    .then((response) => {
      setReviews(response.data);
    });
  }, [])

  const approveReview = (index, id) => {
    patch(`/api/admin/reviews/${id}`, { approve: true });

    let newReviews = [...reviews];
    newReviews.splice(index, 1)
    setReviews(newReviews)
  }

  const rejectReview = (index, id) => {
    patch(`/api/admin/reviews/${id}`, { approve: false });

    let newReviews = [...reviews];
    newReviews.splice(index, 1)
    setReviews(newReviews)
  }

  if (reviews == null)
    return null

  return ( 
    <div>
      <h1 className="text-2xl text-left text-gray-400 mb-6 font-sans">
        Pending reviews:
        <span className="text-gray-800"> {reviews.length}</span>
      </h1>

      <div className="flex flex-col divide-y divide-dashed h-full border-0.5 rounded-lg gap-y-8">
        {reviews.map((review, index) => 
        <div key={index} className="block rounded-lg">
          <div className="grid grid-cols-10 pt-3">
            <div className="block col-span-7">
              <h1 className="text-xl text-gray-700 font-bold tracking-tight my-auto text-left">
                  {review.customer.firstName} {review.customer.lastName}
                  <span className="font-normal">
                    &nbsp;for ad&nbsp;
                  </span>
                  {review.advertisement.title}
                  <span className="font-normal">
                    &nbsp;by&nbsp;
                  </span>
                  {review.advertisement.advertiser.firstName} {review.advertisement.advertiser.lastName}
              </h1>
              <div className="text-left mt-4">
              {review.comment}
              </div>
              <div className="flex mt-4 text-gray-600 text-sm">
                <Icon className="mr-2" icon="tabler:clock" inline={true} width="20" /> 
                <span className="mr-4">{Moment(review.createdAt).format('D MMM YYYY HH:mm')}</span>
                {Array.from({ length: review.rating }, (_, i) => 
                  <Icon key={i} icon="ic:round-star" inline={true} width="20" /> 
                )}
                {Array.from({ length: 5 - review.rating }, (_, i) => 
                  <Icon key={i} icon="ic:round-star-border" inline={true} width="20" /> 
                )}
              </div>
            </div>
            <div className="block col-span-3">
              <button className="bg-gray-300 hover:bg-gray-400 active:bg-gray-500 drop-shadow-md
              text-gray-700 rounded-lg py-2.5 lg:py-2 text-sm lg:text-base w-1/3 mb-1.5 mt-3 md:mt-0"
              onClick={() => rejectReview(index, review.id)}>
                Reject
              </button>
              <button className="bg-teal-600 hover:bg-teal-700 active:bg-teal-800 drop-shadow-md
              text-white rounded-lg py-2.5 lg:py-2 text-sm lg:text-base w-1/3 ml-2 mb-1.5 mt-3 md:mt-0"
              onClick={() => approveReview(index, review.id)}>
                Approve
              </button>
            </div>
          </div>
        </div>
        )}
      </div>
    </div>
   );
}
 
export default ReviewsPage;