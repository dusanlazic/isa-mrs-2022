// main components
import UserProfileMainInfo from '../components/profile/main/UserProfileMainInfo'

// additional components
import ClientReviewList from '../components/profile/additional/ClientReviewList'
import ClientReservationHistory from '../components/profile/additional/ClientReservationHistory'

// sidebar components
import LoyaltyProgramCard from '../components/profile/sidebar/LoyaltyProgramCard'

import AdditionalInformation from '../components/profile/additional/AdditionalInformation'
import Sidebar from '../components/profile/sidebar/Sidebar'

const clientAdditionalComponents = [
  { title: 'Reviews', component: <ClientReviewList />},
  { title: 'Reservation History',  component: <ClientReservationHistory/>},
]

const clientSidebarComponents = [<LoyaltyProgramCard/>]

const clientMainComponent = <UserProfileMainInfo/>

const ProfilePage = () => {
  
  return (
    <div className="block lg:flex items-start py-24 px-4 md:px-20 lg:px-24 xl:px-44 w-full font-display">
      {/* Main part */}
      <div className="block w-full">
        {/* Main profile info */}
        {clientMainComponent}

        {/* Sidebar on smaller screens is below main info */}
        <div className="block lg:hidden w-full">
          <Sidebar components={clientSidebarComponents}/>
        </div>
        
        {/* Profile Content */}
        <AdditionalInformation options={clientAdditionalComponents}/>
      
      </div>

      {/* Sidebar */}
      <div className="hidden lg:block w-2/5 xl:w-1/3 ">
        <Sidebar components={clientSidebarComponents}/>
      </div>
      
    </div>
   );
}
 
export default ProfilePage;