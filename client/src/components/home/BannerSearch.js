import Navbar from "../util/Navbar";

const BannerSearch = () => {

  const divStyle = {
    backgroundImage: 'url(/images/dual-cabin.jpg)',
    backgroundRepeat: 'no-repeat',
    backgroundPosition: 'center top'
  };

  return ( 
    <div className="w-full h-84 lg:h-120 rounded-xl">
      <div style={divStyle}
      className="flex flex-col h-full bg-cover rounded-xl">
        <Navbar/>
      </div>
    </div>
   );
}
 
export default BannerSearch;