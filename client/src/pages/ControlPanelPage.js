import { useEffect, useState } from "react"
import ReservationHistory from "../components/control_panel/ReservationHistory";
import Sidebar from "../components/control_panel/Sidebar";

const ControlPanel = ({}) => {

	const [currentComponent, setCurrentComponent] = useState("reservations");

  return (
    <div className="flex min-h-screen">
    <Sidebar currentComponent={currentComponent} setCurrentComponent={setCurrentComponent} />
    {
      currentComponent == "reservations" ? <ReservationHistory /> : <div></div>
    }
    </div>
  );
}
export default ControlPanel;