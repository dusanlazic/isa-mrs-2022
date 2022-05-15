import BannerSearch from "../components/home/BannerSearch";
import MainEntityLinks from "../components/home/MainEntityLinks";
import AdvertiserCatch from "../components/home/AdvertiserCatch";

const HomePage = () => {
  return ( 
    <div className="bg-gray-green sm:px-12 lg:px-24 xl:px-48 sm:pt-10">
      <BannerSearch/>
      <MainEntityLinks/>
      <AdvertiserCatch/>
      <div className="min-h-screen"></div>
    </div>
   );
}
 
export default HomePage;