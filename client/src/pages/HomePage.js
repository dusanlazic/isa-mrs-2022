import BannerSearch from "../components/home/BannerSearch";
import MainEntityLinks from "../components/home/MainEntityLinks";
import AdvertiserCatch from "../components/home/AdvertiserCatch";
import TopSix from "../components/home/TopSix";

const HomePage = () => {

  return ( 
    <div className="bg-gray-green sm:px-12 lg:px-24 xl:px-48 sm:py-10">
      <BannerSearch/>
      <MainEntityLinks/>
      <TopSix/>
      <AdvertiserCatch/>
    </div>
   );
}
 
export default HomePage;