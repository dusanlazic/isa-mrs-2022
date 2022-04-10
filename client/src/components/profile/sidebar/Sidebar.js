

const Sidebar = ({components}) => {
  return ( 
    <div className="flex flex-col gap-y-3 mt-4 lg:mt-0 lg:ml-4 text-base md:text-sm xl:text-base">
      {components.map(component => 
        <div key={components.indexOf(component)}>{component}</div>
      )}
    </div>
   );
}
 
export default Sidebar;