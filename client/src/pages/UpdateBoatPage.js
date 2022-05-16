import { useState, useEffect } from "react";
import { useParams } from 'react-router-dom'
import { get } from "../adapters/xhr";

import BoatInfoUpdateEditor from "../components/boat/BoatInfoUpdateEditor";

const UpdateBoatPage = () => {
  const [advertisementData, setAdvertisementData] = useState(null);
  const { id } = useParams();

  useEffect(() => {
    get(`/api/ads/boats/${id}`)
    .then((response) => {
      console.log(response.data)
      setAdvertisementData(response.data);
    });
  }, [])

  if (advertisementData === null) {
    return null;
  }

  return ( 
    <div className="block min-h-screen p-32 px-8 sm:px-20 md:px-52 lg:px-60 xl:px-96 w-full font-display">

      <div className="flex flex-col divide-y divide-dashed
       h-full border-0.5 rounded-lg gap-y-4">
        <BoatInfoUpdateEditor data={advertisementData} advertisementId={id}/>
      </div>

    </div>
   );
}
 
export default UpdateBoatPage;