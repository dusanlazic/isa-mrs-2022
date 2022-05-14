import Navbar from "../util/Navbar";
import SearchBar from "./SearchBar";

const BannerSearch = () => {

  const divStyle = {
    backgroundImage: 'url(/images/dual-cabin.jpg)',
    backgroundRepeat: 'no-repeat',
    backgroundPosition: 'center top'
  };

  return ( 
    <div className="w-full h-84 lg:h-120 rounded-xl">
      <div style={divStyle}
      className="flex flex-col justify-between h-full bg-cover rounded-xl">
        <Navbar/>
        <SearchBar/>
      </div>
    </div>
   );
}
 
export default BannerSearch;