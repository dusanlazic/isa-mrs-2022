const HourlyPriceList = () => {
    const prices = [
      {
        id: 0,
        text: 'Price per hour for less than 3 hours',
        price: 15,
      }, 
      {
        id: 1,
        text: 'Price per hour for more than 3 hours',
        price: 10,
      },
    ]
  
    return (
      <div className="border border-gray-100 rounded-lg text-left
      shadow-sm py-3 px-5 tracking-wide">
        <h1 className="text-xl font-medium text-gray-900">Prices</h1>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-1 gap-y-3">
          {prices.map(tag => 
          <div key={tag.id}>
            <h1 className="text-sm leading-5">{tag.text}</h1>
            <span className="font-mono font-bold text-lg leading-4">{tag.price}€</span>
          </div>  
          )}
        </div>
      </div>
     );
  }
   
  export default HourlyPriceList;