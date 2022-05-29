const PriceCard = ({data}) => {
  
    return (
      <div className="border border-gray-100 rounded-lg text-left
      shadow-sm py-3 px-5 tracking-wide">
        <h1 className="text-xl font-medium text-gray-900">Price</h1>
        <div className="font-mono text-lg">
          <span className="font-bold text-4xl">
            {window.location.href.includes('adventure') ? data.pricePerPerson : data.pricePerDay}
          </span>
          <span className="text-4xl">
            {data.currency}
          </span>
          <span>{window.location.href.includes('adventure') ? "/person" : "/day"}</span>
        </div>
        { !window.location.href.includes('resort') && 
          <div className="text-xs leading-5 mt-5">
            Cancellation fee is <span className="font-mono font-bold text-sm leading-4">{data.cancellationFee} {data.currency}</span>
          </div>
        }
      </div>
     );
  }
   
  export default PriceCard;