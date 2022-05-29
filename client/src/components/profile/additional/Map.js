import { MapContainer, TileLayer, Marker, Popup, useMapEvents } from 'react-leaflet'
import L from 'leaflet';


const customMarker = new L.icon({
  iconUrl: '/images/icons/location-pointer.svg',
  iconSize: [35, 46],
  iconAnchor: [17, 46]
});

L.Marker.prototype.options.icon = customMarker;

function LocationFinderDummy({func}) {
  const map = useMapEvents({
      click(e) {
          console.log(e.latlng);
          func(e.latlng);
      },
  });
  return null;
}

const Map = ({allowChange, data, coordinates, changeCoordinates }) => {

  return ( 
    <div className='rounded-lg overflow-hidden'>
      <MapContainer center={coordinates} zoom={13} className="h-120 w-full rounded-lg z-20">
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        {allowChange && <LocationFinderDummy func={changeCoordinates}/>}
        <Marker position={coordinates}>
          {!allowChange && data != null &&
            <Popup minWidth={125}>
              {data.photos.length > 0 &&
              <img src={"/api/" + data.photos[0].uri} alt="" key={data.photos[0].uri} className='rounded-md mb-2' />}
              <p className='text-center'>{data.title}</p>
            </Popup>
            }
          
        </Marker>
      </MapContainer>
    </div>
   );
}
 
export default Map;