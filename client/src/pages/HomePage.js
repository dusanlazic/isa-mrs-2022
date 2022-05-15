import BannerSearch from "../components/home/BannerSearch";
import MainEntityLinks from "../components/home/MainEntityLinks";
import AdvertiserCatch from "../components/home/AdvertiserCatch";
import TopTens from "../components/home/TopTens";

const HomePage = () => {
  return ( 
    <div className="bg-gray-green sm:px-12 lg:px-24 xl:px-48 sm:py-10">
      <BannerSearch/>
      <MainEntityLinks/>
      <TopTens/>
      <AdvertiserCatch/>
    </div>
   );
}
 
export default HomePage;