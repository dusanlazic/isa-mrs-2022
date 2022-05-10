import { useEffect, useState } from "react"
import { get } from "../../../adapters/xhr";
import ClientReview from './ClientReview'

const AdvertisementReviewList = ({data}) => {

	const [reviews, setReviews] = useState(null);

	useEffect(() => {
		get(`/api/ads/${data.id}/reviews`).then((response) => {
			setReviews(response.data);
		  });
		}, [])

    
  if (reviews === null) {
    return null;
  }

  return ( 
    <div className="flex flex-col gap-y-6">
      {reviews.map((review) => 
        <ClientReview review={review} key={review.id}/>
      )}
    </div>
   );
}
 
export default AdvertisementReviewList;