import { useState, useEffect } from "react";
import { useParams, useNavigate } from 'react-router-dom'
import { get } from "../adapters/xhr";
import { getSession } from "../contexts";

import AdventureInfoUpdateEditor from "../components/adventure/AdventureInfoUpdateEditor";
import BoatInfoUpdateEditor from "../components/boat/BoatInfoUpdateEditor";
import ResortInfoUpdateEditor from "../components/resort/ResortInfoUpdateEditor";

let ComponentToDisplay;
const UpdateAdvertisementPage = () => {
  const [advertisementData, setAdvertisementData] = useState(null);
  const { id } = useParams();
  
  const navigate = useNavigate();
  useEffect(() => {
    const type = getAdvertisementType();
    get(`/api/ads/${type}/${id}`)
    .then((response) => {
      response.data.advertiser.id === getSession().id ? setAdvertisementData(response.data) : navigate('/notfound');
      decideComponentToDisplay(type);
    });
  }, [])

  const getAdvertisementType = () => {
    if (window.location.href.includes('resort')) return 'resorts';
    else if (window.location.href.includes('boat')) return 'boats';
    else if (window.location.href.includes('adventure')) return 'adventures';
  }

  const decideComponentToDisplay = (type) => {
    if (type === "resorts") ComponentToDisplay = ResortInfoUpdateEditor;
    else if (type === "boats") ComponentToDisplay = BoatInfoUpdateEditor;
    else if (type === "adventures") ComponentToDisplay = AdventureInfoUpdateEditor;
  }

  if (advertisementData === null || ComponentToDisplay === undefined) {
    return null;
  }

  return ( 
    <div className="block min-h-screen p-32 px-8 sm:px-20 md:px-52 lg:px-60 xl:px-96 w-full font-display">

      <div className="flex flex-col divide-y divide-dashed
       h-full border-0.5 rounded-lg gap-y-4">
        <ComponentToDisplay data={advertisementData} advertisementId={id}/>
      </div>

    </div>
   );
}
 
export default UpdateAdvertisementPage;