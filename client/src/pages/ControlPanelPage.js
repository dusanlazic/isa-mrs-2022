import { useState } from "react"
import AdventureInfoEditor from "../components/adventure/AdventureInfoEditor";
import BoatInfoEditor from "../components/boat/BoatInfoEditor";
import ResortInfoEditor from "../components/resort/ResortInfoEditor";
import ReservationHistory from "../components/control_panel/ReservationHistory";
import Sidebar from "../components/control_panel/Sidebar";
import { getSession } from "../contexts";

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

	const [currentComponent, setCurrentComponent] = useState("reservations");

  return (
    <div className="flex min-h-screen">
      <Sidebar currentComponent={currentComponent} setCurrentComponent={setCurrentComponent} />
      <div className="p-32 px-8 md:px-8 lg:px-20 xl:px-40 w-full font-display md:ml-80">
        {
        currentComponent === "reservations" ? <ReservationHistory /> : 
        currentComponent === "newEntity" ? getNewEntityPage() : 
        <div></div>
        }
      </div>
    </div>
  );
}
export default ControlPanel;