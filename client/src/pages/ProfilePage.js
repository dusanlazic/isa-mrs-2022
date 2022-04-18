import { useEffect, useState } from 'react'
import {useParams} from 'react-router-dom'
import { get } from "../adapters/xhr";

// main components
import UserProfileMainInfo from '../components/profile/main/UserProfileMainInfo'
import ResortProfileMainInfo from '../components/profile/main/ResortProfileMainInfo'
import BoatProfileMainInfo from '../components/profile/main/BoatProfileMainInfo'

// additional components
import ClientReviewList from '../components/profile/additional/ClientReviewList'
import ClientReservationHistory from '../components/profile/additional/ClientReservationHistory'
import About from '../components/profile/additional/AboutTab'
import Gallery from '../components/profile/additional/Gallery'

// sidebar components
import LoyaltyProgramCard from '../components/profile/sidebar/LoyaltyProgramCard'
import HourlyPriceCard from '../components/profile/sidebar/HourlyPriceCard'
import DailyPriceCard from '../components/profile/sidebar/DailyPriceCard'

import AdditionalInformation from '../components/profile/additional/AdditionalInformation'
import Sidebar from '../components/profile/sidebar/Sidebar'

import Equipment from '../components/profile/sidebar/Equipment'

const type = 'boat';

const clientAdditionalComponents = [
  { title: 'Reviews', component: <ClientReviewList />},
  { title: 'Reservation History',  component: <ClientReservationHistory/>},
];

const clientSidebarComponents = [<LoyaltyProgramCard/>];

const clientMainComponent = UserProfileMainInfo;

const resortAdditionalComponents = [
  { title: 'About', component: <About />},
  { title: 'Photos',  component: <Gallery/>},
  { title: 'Reviews', component: <ClientReviewList />},
  //{ title: 'Location', component: <Map />},
];

const resortSidebarComponents = [<DailyPriceCard/>];

const resortMainComponent = ResortProfileMainInfo;

const boatAdditionalComponents = [
  { title: 'About', component: <About />},
  { title: 'Photos',  component: <Gallery/>},
  { title: 'Reviews', component: <ClientReviewList />},
  //{ title: 'Location', component: <Map />},
];

const boatSidebarComponents = [<HourlyPriceCard/>, <Equipment />];

const boatMainComponent = BoatProfileMainInfo;

const ProfilePage = () => {

  let sidebarComponents, MainComponent, contentComponents, endpoint;

  if (window.location.href.includes('resort')) {
    sidebarComponents = resortSidebarComponents;
    MainComponent = resortMainComponent;
    contentComponents = resortAdditionalComponents;
    endpoint = '/ads/resorts';
  }
  else if (window.location.href.includes('client')) {
    sidebarComponents = clientSidebarComponents;
    MainComponent = clientMainComponent;
    contentComponents = clientAdditionalComponents;
    endpoint = '/customers';
  }
  else {
    sidebarComponents = boatSidebarComponents;
    MainComponent = boatMainComponent;
    contentComponents = boatAdditionalComponents;
    endpoint = '/ads/boats';
  }

  let { id } = useParams();
  const [profileData, setProfileData] = useState(null);

  // main api call
  useEffect(() => {
    get(`/api${endpoint}/${id}`)
    .then((response) => {
      console.log(response.data)
      setProfileData(response.data);
    });
  }, [])

  if (profileData === null) {
    return null;
  }

  return (
    <div className="block lg:flex items-start py-24 px-4 md:px-20 lg:px-24 xl:px-44 w-full font-display">
      {/* Main part */}
      <div className="block w-full">
        {/* Main profile info */}
        {<MainComponent data={profileData}/>}

        {/* Sidebar on smaller screens is below main info */}
        <div className="block lg:hidden w-full">
          <Sidebar components={sidebarComponents}/>
        </div>
        
        {/* Profile Content */}
        <AdditionalInformation options={contentComponents}/>
      
      </div>

      {/* Sidebar */}
      <div className="hidden lg:block w-2/5 xl:w-1/3 ">
        <Sidebar components={sidebarComponents}/>
      </div>
      
    </div>
   );
}
 
export default ProfilePage;