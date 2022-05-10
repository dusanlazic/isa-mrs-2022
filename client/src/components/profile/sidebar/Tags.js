const Tags = ({data}) => {

    return (
      <div className="border border-gray-100 rounded-lg text-left
      shadow-sm py-3 px-5 tracking-wide">
        <h1 className="text-xl font-medium text-gray-900">Tags</h1>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-1 gap-y-3">
          {data.tags.map(tag =>
          <div key={tag.name}>
            <h1 className="text-sm leading-5">&#10003; {tag.name}</h1>
          </div>  
          )}
        </div>
      </div>
     );
  }
   
  export default Tags;