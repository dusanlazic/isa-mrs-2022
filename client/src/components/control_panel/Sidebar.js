import { useState } from "react"
import { Icon } from '@iconify/react';
import { getSession } from "../../contexts";

const getNewEntityName = () => {
  const session = getSession();
  if (session.accountType === "FISHING_INSTRUCTOR_OWNER"){
    return "adventure";
  }
  else if (session.accountType === "BOAT_OWNER"){
    return "boat";
  }
  else {
    return "resort";
  }
}

const Sidebar = ({ currentComponent, setCurrentComponent }) => {

	const [sidebarOpen, setSidebarOpen] = useState(false);

  function handleSidebar(b) {
    setSidebarOpen(b)

    // 320px = 20rem = w-80 (tailwind)
    var sideBar = document.getElementById("mobile-nav");
    if (b){
      sideBar.style.transform = "translateX(-225px)";
    }
    else {
      sideBar.style.transform = "translateX(0px)";
    }
  }

  const sidebarItems =[
    { name:"reservations", text:"Reservation history", icon:"tabler:list-search" },
    { name:"newEntity", text:`New ${getNewEntityName()}`, icon:"tabler:new-section" },
    { name:"reports", text:"Reports", icon:"tabler:clipboard-list" },
  ];

  return (
    <div className="block fixed z-20 mt-10">
      <div className="h-screen w-80 top-0 bottom-0 overflow-y-scroll overflow-x-hidden no-scrollbar hidden md:block bg-gray-100">
        <div className="pt-12 p-6 h-full">
          {sidebarItems.map(item => 
            <div key={item.name} onClick={() => setCurrentComponent(item.name)} className={'flex text-lg px-4 py-2 tracking-wide rounded-lg cursor-pointer hover:bg-gray-300 hover:text-gray-800' + (currentComponent === item.name ? 'bg-gray-200 text-gray-800' : 'text-gray-500')}>
              <span className='w-8 '>
                <Icon icon={item.icon} height="27"/>
              </span>
              <div>{item.text}</div>
            </div>
          )}
        </div>
      </div>

      <div className="h-screen fixed z-50 bg-gray-100 shadow md:h-full flex-col justify-between md:hidden transition duration-150 ease-in-out" id="mobile-nav">
        {!sidebarOpen ? 
        <div className="h-10 w-10 bg-gray-800 absolute right-0 mt-16 -mr-10 flex items-center shadow rounded-tr rounded-br justify-center cursor-pointer text-white" onClick={() => handleSidebar(true)}>
          <Icon icon="tabler:x" height="20" width="20"/>
        </div>
          :
        <div id="closeSideBar" className="md:hidden h-10 w-10 bg-gray-800 absolute right-0 mt-16 -mr-10 flex items-center shadow rounded-tr rounded-br justify-center cursor-pointer text-white"onClick={() => handleSidebar(false)}>
          <Icon icon="tabler:adjustments" height="20" width="20"/>
        </div>
        }
          

        <div className="mt-6 space-y-1">
          <div className="mt-6 space-y-1">
            {sidebarItems.map(item => 
              <div key={item.name} onClick={() => setCurrentComponent(item.name)} className={'flex text-lg px-4 py-2 tracking-wide rounded-lg cursor-pointer hover:bg-gray-300 hover:text-gray-800' + (currentComponent === item.name ? 'bg-gray-200 text-gray-800' : 'text-gray-500')}>
                <span className='w-8 '>
                  <Icon icon={item.icon} height="27"/>
                </span>
                <div>{item.text}</div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Sidebar;