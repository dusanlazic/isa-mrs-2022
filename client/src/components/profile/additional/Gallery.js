import { useState } from "react";
import { Icon } from "@iconify/react";

const Gallery = ({data}) => {
  const [selectedItem, setSelectedItem] = useState(0);

	return (
    <div>
      {data.photos.length === 0 &&
        <h1 className="text-xl font-medium text-gray-900">No photos are present.</h1>
      }
      {data.photos.length > 0 &&
      <div className="relative flex flex-col justify-center w-full">

        {/* arrows */}
        <a className="absolute left-0 z-10 my-auto cursor-pointer"
        onClick={() => setSelectedItem(selectedItem - 1 < 0 ? selectedItem : selectedItem - 1)}
        href={`#item${selectedItem}`}>
          <Icon icon="tabler:chevron-left" className="w-12 h-12 my-auto text-white"/>
        </a>
        <a className="absolute right-0 z-10 my-auto  cursor-pointer"
        onClick={() => setSelectedItem(selectedItem + 1 >= data.photos.length ? selectedItem : selectedItem + 1)}
        href={`#item${selectedItem}`}>
          <Icon icon="tabler:chevron-right" className="w-12 h-12 my-auto text-white"/>
        </a>

        <div className="relative first-letter:w-full h-120 carousel carousel-center rounded-md overflow-hidden">
          {data.photos.map((image) => 
            // <img src={"/api/" + image.uri} alt="" key={image.uri}
            // className="h-full rounded-md object-cover" />
            
          <div className="carousel-item w-full">
            <img id={`item${data.photos.indexOf(image)}`} src={"/api/" + image.uri}
            className="w-full object-cover" alt="Tailwind CSS Carousel component" />

          </div>
          )}
        </div>

        <div className="flex justify-center w-full mt-4 gap-x-4">
          {data.photos.map((image) => 
            <a href={`#item${data.photos.indexOf(image)}`} onClick={() => setSelectedItem(data.photos.indexOf(image))}>
              {/* <img src={"/api/" + image.uri} className="w-20 h-14 rounded-xl object-cover" alt="" /> */}
              <div className={`w-3 h-3 rounded-full border-2 border-raisin-black
              ${selectedItem === data.photos.indexOf(image) ? 'bg-raisin-black' : 'bg-white'}`}></div>
            </a>
          )}
        </div>

      </div>
      }
    </div>
		
	);
}

export default Gallery;