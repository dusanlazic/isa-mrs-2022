const Equipment = () => {
    const equipment = [
      {
        id: 0,
        name: 'Quality fishing rod'
      },
      {
        id: 1,
        name: 'Lead snikers'
      },
      {
        id: 2,
        name: 'Fishing net'
      },
      {
        id: 3,
        name: 'Bait'
      },
      {
        id: 3,
        name: 'Spinning reels'
      },
      {
        id: 3,
        name: 'Bite indicators'
      },
    ]
  
    return (
      <div className="border border-gray-100 rounded-lg text-left
      shadow-sm py-3 px-5 tracking-wide">
        <h1 className="text-xl font-medium text-gray-900">Equipment</h1>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-1 gap-y-3">
          {equipment.map(tag =>
          <div key={tag.id}>
            <h1 className="text-sm leading-5">&#10003; {tag.name}</h1>
          </div>  
          )}
        </div>
      </div>
     );
  }
   
  export default Equipment;