import { useState } from "react";
import { post } from "../../adapters/xhr";
import { useNavigate } from 'react-router-dom';
import ReactFlagsSelect from "react-flags-select";

const BoatInfoEditor = () => {
  const [title, setTitle] = useState(null);
  const [description, setDescription] = useState(null);
  const [availableAfter, setAvailableAfter] = useState(null);
  const [availableUntil, setAvailableUntil] = useState(null);
  const [rules, setRules] = useState(null);
  const [currency, setCurrency] = useState(null);
  const [capacity, setCapacity] = useState(null);
  const [cancellationFee, setCancellationFee] = useState(null);
  const [address, setAddress] = useState(null);
  const [countryCode, setCountryCode] = useState(null);
  const [city, setCity] = useState(null);
  const [postalCode, setPostalCode] = useState(null);
  const [state, setState] = useState(null);
  const [tags, setTags] = useState(null);
  const [fishingEquipment, setFishingEquipment] = useState(null);
  const [pricingDescription, setPricingDescription] = useState(null);
  const [optionsInputFields, setOptionsInputFields] = useState([{ name: '', description: '', maxCount: '' }])
  const [pricesInputFields, setPricesInputFields] = useState([{ value: '', minDays: '' }])
  const [photoPreviews, setPhotoPreviews] = useState([])
  const [photoIds, setPhotoIds] = useState([])
  const [boatType, setBoatType] = useState([null])
  const [boatLength, setBoatLength] = useState([null])
  const [engineNumber, setEngineNumber] = useState([null])
  const [enginePower, setEnginePower] = useState([null])
  const [boatSpeed, setBoatSpeed] = useState([null])
  const [checkIn, setCheckIn] = useState([null])
  const [checkOut, setCheckOut] = useState([null])
  const [navigationalEquipment, setNavigationalEquipment] = useState(null);

  const navigate = useNavigate();

  const createAd = () => {
    post(`/api/ads/boats`, {
      description: description,
      cancellationFee: cancellationFee,
      title: title,
      capacity: capacity,
      currency: currency,
      rules: rules,
      availableAfter: availableAfter,
      availableUntil: availableUntil,
      pricingDescription: pricingDescription,
      address: {
        address: address,
        postalCode: postalCode,
        city: city,
        countryCode: countryCode,
        state: state,
        latitude: "0.0",
        longitude: "0.0"
      },
      fishingEquipmentNames: fishingEquipment.split(/[\s,]+/),
      navigationalEquipmentNames: navigationalEquipment.split(/[\s,]+/),
      tagNames: tags.split(/[\s,]+/),
      options: Array.from(optionsInputFields),
      prices: Array.from(pricesInputFields),
      photoIds: photoIds,
      boatType: boatType,
      boatLength: boatLength,
      engineNumber: engineNumber,
      enginePower: enginePower,
      boatSpeed: boatSpeed,
      checkInTime: checkIn,
      checkOutTime: checkOut
    })
      .then((response) => {
        alert(response.data);
        navigate(`/adventure/${response.headers['location'].split("/").pop()}`);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  }

  const handleOptionsChange = (index, event) => {
    let data = [...optionsInputFields];
    data[index][event.target.name] = event.target.value;
    setOptionsInputFields(data);
  }

  const addOptionField = () => {
    let newField = { name: '', description: '', maxCount: '' }
    setOptionsInputFields([...optionsInputFields, newField]);
  }

  const removeOptionField = (index) => {
    let data = [...optionsInputFields];
    data.splice(index, 1);
    setOptionsInputFields(data);
  }

  const handlePricesChange = (index, event) => {
    let data = [...pricesInputFields];
    data[index][event.target.name] = event.target.value;
    setPricesInputFields(data);
  }

  const addPriceField = () => {
    let newField = { value: '', minHours: '' };
    setPricesInputFields([...pricesInputFields, newField])
  }

  const removePriceField = (index) => {
    let data = [...pricesInputFields];
    data.splice(index, 1)
    setPricesInputFields(data)
  }

  const uploadImage = () => {
    const file = document.getElementById('image-input').files[0];
    const data = new FormData();
    data.append('file', file);

    post(`/api/photos/upload`, data)
      .then((response) => {
        setPhotoIds([...photoIds, response.data.id])
        setPhotoPreviews([...photoPreviews, "/api" + response.data.uri])
      });
  }

  const removeImage = (index) => {
    let ids = [...photoIds];
    let previews = [...photoPreviews];

    ids.splice(index, 1)
    previews.splice(index, 1)

    setPhotoIds(ids);
    setPhotoPreviews(previews);
  }

  return (
    <div className="block w-full">
      <h1 className="text-2xl text-left text-gray-400 font-sans">Create a new advertisement for your adventure</h1>

      {/* Basic info */}
      <h2 className="text-xl text-left text-gray-800 font-sans mt-4">Basic information ℹ️</h2>

      <div className="grid grid-cols-3 mt-2">
        <div className="block col-span-3 text-left">
          <label className="text-xs">title</label>
          <input autoComplete="off" placeholder="title"
            onChange={(event) => { setTitle(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>
      </div>

      <div className="grid grid-cols-3 mt-2 gap-x-3">
        <div className="block col-span-3 text-left">
          <label className="text-xs">description</label>
          <textarea placeholder="tell the world about your offer"
            onChange={(event) => { setDescription(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"
            rows="5" />
        </div>
      </div>

      {/* Boat specials */}
      <div className="grid grid-cols-3 mt-2 gap-x-3">
        <div className="block col-span-1 text-left">
          <label className="text-xs">capacity</label>
          <input autoComplete="off" onChange={(event) => { setCapacity(event.target.value) }} type="number" min={1}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">type</label>
          <input autoComplete="off" onChange={(event) => { setBoatType(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">length</label>
          <input autoComplete="off" onChange={(event) => { setBoatLength(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
            focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">engine number</label>
          <input autoComplete="off" onChange={(event) => { setEngineNumber(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
            focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">engine power</label>
          <input autoComplete="off" onChange={(event) => { setEnginePower(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
            focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">speed</label>
          <input autoComplete="off" onChange={(event) => { setBoatSpeed(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
            focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>
      </div>

      {/* Photos */}
      <h2 className="text-xl text-left text-gray-800 font-sans mt-12">Photos 📸</h2>
      <div className="grid grid-cols-10 gap-x-6 mt-4">
        <div className="block col-span-1">
          <div className="flex rounded-lg w-full ml-1">
            <label id="label-input" className="outline-dashed outline-2 outline-offset-2 outline-gray-400
              hover:outline-gray-600
            hover:border-gray-300 cursor-pointer">

              <img id="image-preview" src={'/images/fish_guy_gray.jpg'}
                className="flex-none w-24 h-24 rounded-xl object-cover" />

              <input type="file" accept="image/*" onChange={() => uploadImage()} id="image-input"
                className="opacity-0 hidden h-0 w-0" />

            </label>
          </div>
        </div>

        {photoPreviews.map((preview, index) => {
          return (
            <div key={index} className="block col-span-1">
              <div className="flex rounded-lg w-full ml-1">
                <label id="label-input" className="outline-dashed outline-2 outline-offset-2 outline-gray-400
              hover:outline-red-600
            hover:border-gray-300 cursor-pointer">

                  <img id="image-preview" src={preview}
                    className="flex-none w-24 h-24 rounded-xl object-cover" onClick={() => removeImage(index)} />

                </label>
              </div>
            </div>
          )
        })}
      </div>

      {/* Location info */}
      <h2 className="text-xl text-left text-gray-800 font-sans mt-12">Location 📍</h2>
      <div className="grid grid-cols-3 mt-2 gap-x-3">
        <div className="block col-span-2 text-left">
          <label className="text-xs">address</label>
          <input autoComplete="off" placeholder="address"
            onChange={(event) => { setAddress(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">city</label>
          <input autoComplete="off" placeholder="city"
            onChange={(event) => { setCity(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>
      </div>

      <div className="grid grid-cols-3 mt-2 gap-x-3">
        <div className="block col-span-1 text-left">
          <label className="text-xs">postal code</label>
          <input autoComplete="off" placeholder="postal code"
            onChange={(event) => { setPostalCode(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">state</label>
          <input autoComplete="off" placeholder="state"
            onChange={(event) => { setState(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">country</label>
          <ReactFlagsSelect selectedSize={15} optionsSize={15} searchable={true}
            selected={countryCode}
            onSelect={(code) => setCountryCode(code)}
          />
        </div>
      </div>

      <div className="block text-left mt-4">
        <label className="text-s">Pinpoint location on a map</label>
        <h2 className="text-xl text-left text-gray-400 italic font-sans mt-6">Map for pinpointing is under construction</h2>
      </div>

      {/* Details */}
      <h2 className="text-xl text-left text-gray-800 font-sans mt-12">Details ✅</h2>

      <div className="text-left mt-3">
        <label className="text-xs">rules of conduct</label>
        <textarea placeholder="rules of conduct"
          onChange={(event) => { setRules(event.target.value) }}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"
          rows="5" />
      </div>

      {/* Options */}
      <div className="block text-left mt-4">
        <label className="text-s">Options</label>
      </div>

      {optionsInputFields.map((input, index) => {
        return (
          <div key={index} className="grid grid-cols-12 mt-1 gap-x-3">
            <div className="block col-span-4 text-left">
              <input autoComplete="off" placeholder="option name"
                name="name"
                value={input.name}
                onChange={event => handleOptionsChange(index, event)}
                className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
            </div>

            <div className="block col-span-5 text-left">
              <input autoComplete="off" placeholder="description"
                name="description"
                value={input.description}
                onChange={event => handleOptionsChange(index, event)}
                className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
            </div>

            <div className="block col-span-2 text-left">
              <input autoComplete="off" placeholder="max count"
                name="maxCount"
                value={input.maxCount}
                onChange={event => handleOptionsChange(index, event)}
                type="number"
                className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
            </div>

            <button onClick={() => removeOptionField(index)}>Remove</button>
          </div>
        )
      })}

      <div className="block mt-4">
        <button onClick={addOptionField}>Add option..</button>
      </div>

      {/* Tags */}
      <div className="block text-left mt-4">
        <label className="text-xs">tags</label>
        <input autoComplete="off" placeholder="tags separated by comma"
          onChange={(event) => { setTags(event.target.value) }}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
        focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
      </div>

<div className="block text-left mt-4">
  <label className="text-xs">fishing equpiment</label>
  <input autoComplete="off" placeholder="fishing equipment separated by comma"
    onChange={(event) => { setFishingEquipment(event.target.value) }}
    className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
  focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
</div>

<div className="block text-left mt-4">
  <label className="text-xs">navigational equpiment</label>
  <input autoComplete="off" placeholder="navigational equipment separated by comma"
    onChange={(event) => { setNavigationalEquipment(event.target.value) }}
    className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
  focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
</div>

      {/* Pricing */}
      <h2 className="text-xl text-left text-gray-800 font-sans mt-12">Pricing 💵</h2>
      <div className="grid grid-cols-3 mt-1 gap-x-3">
        <div className="block col-span-1 text-left">
          <label className="text-xs">currency</label>
          <input autoComplete="off" placeholder="e.g. EUR, USD, RSD"
            onChange={(event) => { setCurrency(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">cancellation fee</label>
          <input autoComplete="off" placeholder="cancellation fee"
            onChange={(event) => { setCancellationFee(event.target.value) }}
            type="number"
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>
      </div>

      <div className="block text-left mt-4">
        <label className="text-s">Prices list</label>
      </div>

      {pricesInputFields.map((input, index) => {
        return (
          <div key={index} className="grid grid-cols-12 mt-1 gap-x-3">
            <div className="block col-span-4 text-left">
              <input autoComplete="off" placeholder="price"
                name="value"
                value={input.value}
                onChange={event => handlePricesChange(index, event)}
                type="number"
                className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
            </div>

            <div className="block col-span-4 text-left">
              <input autoComplete="off" placeholder="days required"
                name="minDays"
                value={input.minDays}
                onChange={event => handlePricesChange(index, event)}
                type="number"
                className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
            </div>

            <button className="block col-span-1" onClick={() => removePriceField(index)}>Remove</button>
          </div>
        )
      })}

      <div className="block mt-4">
        <button onClick={addPriceField}>Add price..</button>
      </div>

      <div className="grid grid-cols-2 mt-2 gap-x-3 mt-4">
        <div className="block col-span-2 text-left">
          <label className="text-xs">Pricing description</label>
          <textarea placeholder="additional info about prices"
            onChange={(event) => { setPricingDescription(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"
            rows="3" />
        </div>
      </div>

{/* Availability */}
<h2 className="text-xl text-left text-gray-800 font-sans mt-12">Availability 📅</h2>
<div className="grid grid-cols-4 mt-1 gap-x-3">
  <div className="block col-span-2 text-left">
    <label className="text-xs">available after</label>
    <input
      onChange={(event) => { setAvailableAfter(event.target.value) }}
      autoComplete="off"type="date"
      className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
    focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
  </div>

  <div className="block col-span-2 text-left">
    <label className="text-xs">available until</label>
    <input
      onChange={(event) => { setAvailableUntil(event.target.value) }}
      autoComplete="off"type="date"
      className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
    focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
  </div>
</div>

{/* Ckeck in/out */}
<h2 className="text-xl text-left text-gray-800 font-sans mt-12">Check in and check out 🕑</h2>
<div className="grid grid-cols-4 mt-1 gap-x-3">
  <div className="block col-span-2 text-left">
    <label className="text-xs">check in</label>
    <input
      onChange={(event) => { setCheckIn(event.target.value) }}
      autoComplete="off"type="time"
      className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
    focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
  </div>

  <div className="block col-span-2 text-left">
    <label className="text-xs">check out</label>
    <input
      onChange={(event) => { setCheckOut(event.target.value) }}
      autoComplete="off"type="time"
      className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
    focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
  </div>
</div>

      {/* confirm button */}
      <div className="grid grid-cols-1 md:grid-cols-3 md:gap-x-6 mt-4">
        <div className="flex flex-col justify-end md:col-start-3 text-left w-full">
          <button className="bg-teal-600 hover:bg-teal-700 active:bg-teal-800 w-full drop-shadow-md
          text-white rounded-lg py-2.5 lg:py-2 text-sm lg:text-base mb-1.5 mt-3 md:mt-0"
            onClick={() => { createAd() }}>
            Create advertisement
          </button>
        </div>

      </div>

    </div>
  );
}

export default BoatInfoEditor;