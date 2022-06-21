import { useState } from "react"
import AdventureInfoEditor from "../components/adventure/AdventureInfoEditor";
import BoatInfoEditor from "../components/boat/BoatInfoEditor";
import ResortInfoEditor from "../components/resort/ResortInfoEditor";
import ReservationHistory from "../components/control_panel/ReservationHistory";
import Sidebar from "../components/control_panel/Sidebar";
import { getSession } from "../contexts";
import MyEntities from "../components/control_panel/MyEntities";

const getNewEntityPage = () => {
  const session = getSession();
  if (session.accountType === "FISHING_INSTRUCTOR_OWNER"){
    return <AdventureInfoEditor/>;
  }
  else if (session.accountType === "BOAT_OWNER"){
    return <BoatInfoEditor/>;
  }
  else {
    return <ResortInfoEditor/>;
  }
}

const ControlPanel = ({}) => {

	const [currentComponent, setCurrentComponent] = useState("myEntities");

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
        <div></div>
        }
      </div>
    </div>
  );
}
export default ControlPanel;