import BannerSearch from "../components/home/BannerSearch";
import MainEntityLinks from "../components/home/MainEntityLinks";

const HomePage = () => {
  return ( 
    <div className="bg-baby-green px-12 lg:px-24 xl:px-48 pt-10">
      <BannerSearch/>
      <MainEntityLinks/>
      <div className="min-h-screen"></div>
    </div>
   );
}
 
export default HomePage;