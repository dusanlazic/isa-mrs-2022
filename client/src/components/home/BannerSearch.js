import Navbar from "../util/Navbar";
import SearchBar from "./SearchBar";

const BannerSearch = () => {

  const divStyle = {
    backgroundImage: 'url(/images/dual-cabin.jpg)',
    backgroundRepeat: 'no-repeat',
  };

  return ( 
    <div className="w-full h-84 lg:h-120">
      <div style={divStyle}
      className="flex flex-col justify-between h-full bg-cover rounded-none sm:rounded-t-xl bg-left sm:bg-top">
        <Navbar/>
        <SearchBar/>
      </div>
    </div>
   );
}
 
export default BannerSearch;