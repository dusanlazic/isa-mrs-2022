import { useLocation } from "react-router-dom";

const SearchPage = () => {
  const location = useLocation();
  const urlList = location.pathname.split('/')
  const entity = urlList[urlList.length-1];

  const where = new URLSearchParams(location.search).get("where");
  const startDate = new URLSearchParams(location.search).get("startDate");
  const endDate = new URLSearchParams(location.search).get("endDate");
  const guests = new URLSearchParams(location.search).get("guests");

  return ( 
    <div className="py-20">
      {where} {startDate} {endDate} {guests}
    </div>
   );
}
 
export default SearchPage;