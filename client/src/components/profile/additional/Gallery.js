

const Gallery = ({data}) => {
	return (
    <div>
      {data.photos.length === 0 &&
        <h1 className="text-xl font-medium text-gray-900">No photos are present.</h1>
      }
      {data.photos.length > 0 &&
      <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-x-2 gap-y-2">
	
        {data.photos.map((image) => 
          <img src={"/api/" + image.uri} alt="" key={image.uri}
          className="h-full rounded-md object-cover" />
        )}
      </div>
      }
    </div>
		
	);
}

export default Gallery;