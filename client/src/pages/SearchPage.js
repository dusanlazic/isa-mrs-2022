import { useLocation } from "react-router-dom";

import SearchAndFilter from "../components/search/SearchAndFilter";

const SearchPage = () => {
  const location = useLocation();
  const urlList = location.pathname.split('/')
  const entity = urlList[urlList.length-1];

  const where = new URLSearchParams(location.search).get("where");
  const startDate = new URLSearchParams(location.search).get("startDate");
  const endDate = new URLSearchParams(location.search).get("endDate");
  const guests = new URLSearchParams(location.search).get("guests");

  return ( 
    <div className="py-20 px-6 sm:px-14 md:px-20 lg:px-40 xl:px-64 bg-silver-accent h-screen">
      <SearchAndFilter whereProp={where ? where : ''} guestsProp={guests ? guests : ''}
      entityProp={entity} startDateProp={startDate} endDateProp={endDate} searchResults={123}/>
    </div>
   );
}
 
export default SearchPage;