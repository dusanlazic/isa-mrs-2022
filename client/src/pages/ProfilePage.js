import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { get } from "../adapters/xhr"
import NotFound from './NotFound'

// main components
import UserProfileMainInfo from '../components/profile/main/UserProfileMainInfo'
import ResortProfileMainInfo from '../components/profile/main/ResortProfileMainInfo'
import BoatProfileMainInfo from '../components/profile/main/BoatProfileMainInfo'
import AdventureProfileMainInfo from '../components/profile/main/AdventureProfileMainInfo'

// additional components
import ClientReviewList from '../components/profile/additional/ClientReviewList'
import AdvertisementReviewList from '../components/profile/additional/AdvertisementReviewList'
import ClientReservationHistory from '../components/profile/additional/ClientReservationHistory'
import About from '../components/profile/additional/AboutTab'
import BoatAbout from '../components/profile/additional/BoatAboutTab'
import Gallery from '../components/profile/additional/Gallery'
import Map from '../components/profile/additional/Map'

// sidebar components
import LoyaltyProgramCard from '../components/profile/sidebar/LoyaltyProgramCard'
import HourlyPriceCard from '../components/profile/sidebar/HourlyPriceCard'
import DailyPriceCard from '../components/profile/sidebar/DailyPriceCard'
import Equipment from '../components/profile/sidebar/Equipment'
import Tags from '../components/profile/sidebar/Tags'

import AdditionalInformation from '../components/profile/additional/AdditionalInformation'
import Sidebar from '../components/profile/sidebar/Sidebar'




const clientMainComponent = UserProfileMainInfo;

const boatMainComponent = BoatProfileMainInfo;

const adventureMainComponent = AdventureProfileMainInfo;

const ProfilePage = () => {

  let sidebarComponents, MainComponent, contentComponents, endpoint;

  let { id } = useParams();
  const [profileData, setProfileData] = useState(null);

  if (window.location.href.includes('resort')) {
    endpoint = '/ads/resorts'
  }
  else if (window.location.href.includes('adventure')) {
    endpoint = '/ads/adventures';
  }
  else if (window.location.href.includes('boat')) {
    endpoint = '/ads/boats';
  }
  else if (window.location.href.includes('client')) {
    endpoint = '/customers';
  }

  const navigate = useNavigate();

  // main api call
  useEffect(() => {
    get(`/api${endpoint}/${id}`)
    .then((response) => {
      console.log(response.data)
      setProfileData(response.data);
    })
    .catch((error) => {
      navigate('/notfound');
    });
  }, [])

  if (profileData === null) {
    return null;
  }

  if (window.location.href.includes('resort')) {
    sidebarComponents = [<DailyPriceCard data={profileData} />, <Tags data={profileData} />];
    MainComponent = ResortProfileMainInfo;
    contentComponents = [
      { title: 'About', component: <About data={profileData} />},
      { title: 'Photos',  component: <Gallery data={profileData} />},
      { title: 'Reviews', component: <AdvertisementReviewList data={profileData} />},
      { title: 'Location', component: <Map data={profileData} coordinates={[profileData.address.latitude, profileData.address.longitude]}/>},
    ];
  }
  else if (window.location.href.includes('boat')) {
    sidebarComponents = [<DailyPriceCard data={profileData} />, <Tags data={profileData} />];
    MainComponent = boatMainComponent;
    contentComponents = [
      { title: 'About', component: <BoatAbout data={profileData} />},
      { title: 'Photos',  component: <Gallery data={profileData} />},
      { title: 'Reviews', component: <AdvertisementReviewList data={profileData} />},
      { title: 'Location', component: <Map data={profileData} coordinates={[profileData.address.latitude, profileData.address.longitude]}/>},
    ];
  }
  else if (window.location.href.includes('adventure')) {
    sidebarComponents = [<HourlyPriceCard data={profileData} />, <Tags data={profileData} />];
    MainComponent = adventureMainComponent;
    contentComponents = [
      { title: 'About', component: <About data={profileData} />},
      { title: 'Photos',  component: <Gallery data={profileData} />},
      { title: 'Reviews', component: <AdvertisementReviewList data={profileData} />},
      { title: 'Location', component: <Map data={profileData} coordinates={[profileData.address.latitude, profileData.address.longitude]}/>},
    ];
  }
  else if (window.location.href.includes('client')) {
    sidebarComponents = [<LoyaltyProgramCard/>];
    MainComponent = clientMainComponent;
    contentComponents = [
      { title: 'Reviews', component: <ClientReviewList data = {profileData} /> },
      { title: 'Reservation History',  component: <ClientReservationHistory/> },
    ];
  }

  return (
    <div className="block lg:flex items-start py-24 px-4 md:px-20 lg:px-24 xl:px-44 w-full font-display">
      {/* Main part */}
      <div className="block w-full">
        {/* Main profile info */}
        {<MainComponent data={profileData} advertisementId={id} />}

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