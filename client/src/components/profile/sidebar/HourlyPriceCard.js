const HourlyPriceList = ({data}) => {

    return (
      <div className="border border-gray-100 rounded-lg text-left
      shadow-sm py-3 px-5 tracking-wide">
        <h1 className="text-xl font-medium text-gray-900">Prices</h1>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-1 gap-y-3">
          {data.prices.map(tag => 
          <div key={tag.id}>
            <h1 className="text-sm leading-5">Price per hour for more than {tag.minHours} hours</h1>
            <span className="font-mono font-bold text-lg leading-4">{tag.value} {data.currency}</span>
          </div>  
          )}
        </div>
        <div className="text-xs leading-5 mt-5">
          *Cancellation fee is <span className="font-mono font-bold text-sm leading-4">{data.cancellationFee} {data.currency}</span>
        </div>
      </div>
     );
  }
   
  export default HourlyPriceList;