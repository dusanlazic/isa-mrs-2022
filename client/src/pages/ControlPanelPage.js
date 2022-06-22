import { useState, useEffect } from "react"
import { useNavigate } from 'react-router-dom';

import { getSession } from "../contexts";

import AdventureInfoEditor from "../components/adventure/AdventureInfoEditor";
import BoatInfoEditor from "../components/boat/BoatInfoEditor";
import ResortInfoEditor from "../components/resort/ResortInfoEditor";
import ReservationHistory from "../components/control_panel/ReservationHistory";
import Sidebar from "../components/control_panel/Sidebar";
import MyEntities from "../components/control_panel/MyEntities";
import RegisterAdminPage from '../components/control_panel/RegisterAdminPage';
import RegistrationRequestsPage from '../components/control_panel/RegistrationRequestsPage';
import RemovalRequestsPage from '../components/control_panel/RemovalRequestsPage';
import ReportsPage from '../components/control_panel/ReportsPage';
import ComplaintsPage from '../components/control_panel/ComplaintsPage';
import ReviewsPage from '../components/control_panel/ReviewsPage';
import ManageLoyaltyProgramPage from '../components/control_panel/ManageLoyaltyProgramPage';
import SetCommissionRatePage from '../components/control_panel/SetCommissionRatePage';


const getNewEntityPage = () => {
  const session = getSession();
  if (session.accountType === "FISHING_INSTRUCTOR"){
    return <AdventureInfoEditor/>;
  }
  else if (session.accountType === "BOAT_OWNER"){
    return <BoatInfoEditor/>;
  }
  else {
    return <ResortInfoEditor/>;
  }
}

const decideDefaultComponent = () => {
  const session = getSession();
  if (session.accountType === "ADMIN") return "resolveRegistrationRequests";
  else if (session.accountType === "SUPERUSER") return "registerAdmin";
  else return "myEntities";
}

const ControlPanel = ({}) => {
	const [currentComponent, setCurrentComponent] = useState();
  const navigate = useNavigate();

  useEffect(() => {
    const session = getSession();
    if (session === undefined) 
      navigate(`/notfound`);
    else if (session.accountType === "CUSTOMER") 
      navigate(`/notfound`);
    else
      setCurrentComponent(decideDefaultComponent());
  }, [])

  if (getSession() === undefined) return null;

  return (
    <div className="flex min-h-screen">
      <Sidebar currentComponent={currentComponent} setCurrentComponent={setCurrentComponent} />
      <div className="p-32 px-8 md:px-8 lg:px-20 xl:px-40 w-full font-display md:ml-80">
        {
        currentComponent === "newEntity" ? getNewEntityPage() : 
        currentComponent === "myEntities" ? <MyEntities /> : 
        currentComponent === "reservations" ? <ReservationHistory key={1} type="all"/> :
        currentComponent === "pendingReport" ? <ReservationHistory key={2} type="pendingReport"/> :
        currentComponent === "activeReservations" ? <ReservationHistory key={3} type="activeReservations"/> : 
        currentComponent === "registerAdmin" ? <RegisterAdminPage key={4} /> :
        currentComponent === "resolveRegistrationRequests" ? <RegistrationRequestsPage key={5} /> :
        currentComponent === "resolveRemovalRequests" ? <RemovalRequestsPage key={6} /> :
        currentComponent === "resolveReports" ? <ReportsPage key={7} /> :
        currentComponent === "resolveComplaints" ? <ComplaintsPage key={8} /> :
        currentComponent === "resolveReviews" ? <ReviewsPage key={9} /> :
        currentComponent === "loyaltyConfiguration" ? <ManageLoyaltyProgramPage key={10} /> :
        currentComponent === "commissionRates" ? <SetCommissionRatePage key={11} /> :
        <div></div>
        }
      </div>
    </div>
  );
}
export default ControlPanel;