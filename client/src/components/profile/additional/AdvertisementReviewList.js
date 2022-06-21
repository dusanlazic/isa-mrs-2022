import { useEffect, useState } from "react"
import { get } from "../../../adapters/xhr";
import AdvertisementReview from "./AdvertisementReview";

const AdvertisementReviewList = ({data}) => {

	const [reviews, setReviews] = useState(null);

	useEffect(() => {
		get(`/api/ads/${data.id}/reviews`)
    .then((response) => {
      console.log(response.data);
			setReviews(response.data);
		  });
		}, [])

  return ( 
    <div className="flex flex-col gap-y-6">
      {(reviews === null || reviews.length === 0) &&
        <h1 className="text-xl font-medium text-gray-900">This advertisement hasn't been reviewed yet.</h1>
      }
      {
        reviews !== null && reviews.length > 0 &&
        reviews.map((review, index) => 
          <AdvertisementReview review={review} key={index}/>
        )
      }
    </div>
   );
}
 
export default AdvertisementReviewList;