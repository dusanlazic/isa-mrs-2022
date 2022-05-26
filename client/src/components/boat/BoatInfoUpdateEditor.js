import { useState } from "react";
import { post, put, del } from "../../adapters/xhr";
import { useNavigate } from 'react-router-dom';
import ReactFlagsSelect from "react-flags-select";
import DeletionModal from "../modals/DeletionModal"
import { Icon } from '@iconify/react';

const BoatInfoEditor = ({ data, advertisementId }) => {
  const [showModal, setShowModal] = useState(false);
  const hide = () => setShowModal(false);

  const [title, setTitle] = useState(data.title);
  const [description, setDescription] = useState(data.description);
  const [rules, setRules] = useState(data.rules);
  const [currency, setCurrency] = useState(data.currency);
  const [capacity, setCapacity] = useState(data.capacity);
  const [cancellationFee, setCancellationFee] = useState(data.cancellationFee);
  const [address, setAddress] = useState(data.address.address);
  const [countryCode, setCountryCode] = useState(data.address.countryCode);
  const [city, setCity] = useState(data.address.city);
  const [postalCode, setPostalCode] = useState(data.address.postalCode);
  const [state, setState] = useState(data.address.state);
  const [tags, setTags] = useState(data.tags.join(", "));
  const [fishingEquipment, setFishingEquipment] = useState(data.fishingEquipment.map(item => item.name).join(", "));
  const [pricingDescription, setPricingDescription] = useState(data.pricingDescription);
  const [optionsInputFields, setOptionsInputFields] = useState(data.options);
  const [newOptionsInputFields, setNewOptionsInputFields] = useState([])
  const [pricesInputFields, setPricesInputFields] = useState(data.prices);
  const [newPricesInputFields, setNewPricesInputFields] = useState([])
  const [photoPreviews, setPhotoPreviews] = useState(data.photos.map(item => "/api" + item.uri));
  const [photoIds, setPhotoIds] = useState(data.photos.map(item => item.id));
  const [boatType, setBoatType] = useState(data.boatType)
  const [boatLength, setBoatLength] = useState(data.boatLength)
  const [engineNumber, setEngineNumber] = useState(data.engineNumber)
  const [enginePower, setEnginePower] = useState(data.enginePower)
  const [boatSpeed, setBoatSpeed] = useState(data.boatSpeed)
  const [checkInTime, setCheckInTime] = useState(data.checkInTime);
  const [checkOutTime, setCheckOutTime] = useState(data.checkOutTime);
  const [navigationalEquipment, setNavigationalEquipment] = useState(data.navigationalEquipment.map(item => item.name).join(", "));
  const [availableAfter, setAvailableAfter] = useState(data.availableAfter);
  const [availableUntil, setAvailableUntil] = useState(data.availableUntil);

  const navigate = useNavigate();

  const updateAvailability = () => {
    put(`/api/ads/boats/${advertisementId}/availability-period`, {
      availableAfter: availableAfter === "" ? null : availableAfter,
      availableUntil: availableUntil === "" ? null : availableUntil
    })
      .then((response) => {
        alert(response.data);
        navigate(`/boat/${advertisementId}`);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  }

  const updateAd = () => {
    put(`/api/ads/boats/${advertisementId}`, {
      description: description,
      cancellationFee: cancellationFee,
      title: title,
      capacity: capacity,
      currency: currency,
      rules: rules,
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
      options: Array.from([...optionsInputFields, ...newOptionsInputFields]),
      prices: Array.from([...pricesInputFields, ...newPricesInputFields]),
      photoIds: photoIds,
      boatType: boatType,
      boatLength: boatLength,
      engineNumber: engineNumber,
      enginePower: enginePower,
      boatSpeed: boatSpeed,
      checkInTime: checkInTime,
      checkOutTime: checkOutTime
    })
      .then((response) => {
        alert(response.data);
        navigate(`/boat/${advertisementId}`);
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

  const handleNewOptionsChange = (index, event) => {
    let data = [...newOptionsInputFields];
    data[index][event.target.name] = event.target.value;
    setNewOptionsInputFields(data);
  }

  const addOptionField = () => {
    let newField = { name: '', description: '', maxCount: '' }
    setNewOptionsInputFields([...newOptionsInputFields, newField]);
  }

  const removeOptionField = (index) => {
    let data = [...newOptionsInputFields];
    data.splice(index, 1);
    setNewOptionsInputFields(data);
  }

  const toggleOptionRemoval = (index) => {
    let option = optionsInputFields[index];
    if (option.hasOwnProperty('delete'))
      delete option.delete;
    else
      optionsInputFields[index].delete = true;

    setOptionsInputFields([...optionsInputFields])
  }

  const handlePricesChange = (index, event) => {
    let data = [...pricesInputFields];
    data[index][event.target.name] = event.target.value;
    setPricesInputFields(data);
  }

  const handleNewPricesChange = (index, event) => {
    let data = [...newPricesInputFields];
    data[index][event.target.name] = event.target.value;
    setNewPricesInputFields(data);
  }

  const addPriceField = () => {
    let newField = { value: '', minDays: '' };
    setNewPricesInputFields([...newPricesInputFields, newField])
  }

  const removePriceField = (index) => {
    let data = [...newPricesInputFields];
    data.splice(index, 1)
    setNewPricesInputFields(data)
  }

  const togglePriceRemoval = (index) => {
    let price = pricesInputFields[index];
    if (price.hasOwnProperty('delete'))
      delete price.delete;
    else
      pricesInputFields[index].delete = true;

    setPricesInputFields([...pricesInputFields])
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

  const deleteAdvertisement = () => {
    del(`/api/ads/${advertisementId}`).then((response) => {
      console.log(response);
      setShowModal(false);
      navigate("/");
    });
  }

  return (
    <div className="block w-full">
      <h1 className="text-2xl text-left text-gray-400 font-sans">Edit your boat</h1>

      {/* Basic info */}
      <h2 className="flex text-xl text-left text-gray-800 font-sans mt-12">
        <Icon className="mr-2" icon="tabler:info-circle" inline={true} fontSize={30} />
        <span>Basic information</span>
      </h2>

      <div className="mt-2 text-left">
        <label className="text-xs">title</label>
        <input placeholder="title"
          value={title}
          onChange={(event) => { setTitle(event.target.value) }}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
      </div>

      <div className="mt-2 text-left">
        <label className="text-xs">description</label>
        <textarea placeholder="tell the world about your offer"
          value={description}
          onChange={(event) => { setDescription(event.target.value) }}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"
          rows="5" />
      </div>

      {/* Photos */}
      <h2 className="flex text-xl text-left text-gray-800 font-sans mt-12">
        <Icon className="mr-2" icon="tabler:camera" inline={true} fontSize={30} />
        <span>Photos</span>
      </h2>

      <div className="grid grid-cols-10 gap-x-6 mt-4">
        <div className="block col-span-1">
          <div className="flex rounded-lg w-full ml-1">
            <label id="label-input" className="outline-dashed outline-2 outline-offset-2 outline-gray-400
              hover:outline-gray-600
            hover:border-gray-300 cursor-pointer">

              <img id="image-preview" src={'/images/fish_guy_gray.jpg'} alt=""
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

                  <img id="image-preview" src={preview} alt=""
                    className="flex-none w-24 h-24 rounded-xl object-cover" onClick={() => removeImage(index)} />

                </label>
              </div>
            </div>
          )
        })}
      </div>


      {/* Location */}
      <h2 className="flex text-xl text-left text-gray-800 font-sans mt-12">
        <Icon className="mr-2" icon="tabler:map-pin" inline={true} fontSize={30} />
        <span>Location</span>
      </h2>

      <div className="grid grid-cols-3 mt-2 gap-x-3">
        <div className="block col-span-2 text-left">
          <label className="text-xs">address</label>
          <input placeholder="address" value={address}
            onChange={(event) => { setAddress(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">city</label>
          <input placeholder="city" value={city}
            onChange={(event) => { setCity(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>
      </div>

      <div className="grid grid-cols-3 mt-2 gap-x-3">
        <div className="block col-span-1 text-left">
          <label className="text-xs">postal code</label>
          <input placeholder="postal code" value={postalCode}
            onChange={(event) => { setPostalCode(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">state</label>
          <input placeholder="state" value={state}
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
      <h2 className="flex text-xl text-left text-gray-800 font-sans mt-12">
        <Icon className="mr-2" icon="tabler:list-details" inline={true} fontSize={30} />
        <span>Details</span>
      </h2>

      <div className="text-left mt-3">
        <label className="text-xs">rules of conduct</label>
        <textarea placeholder="rules of conduct" value={rules}
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
        if (input.delete === true) {
          return (
            <div key={index} className="grid grid-cols-12 mt-1 gap-x-3">
              <div className="block col-span-4 text-left">
                <input placeholder="option name"
                  name="name"
                  value={input.name}
                  className="block rounded-lg px-3 border text-gray-300 border-gray-300 text-base py-2
                focus:outline-none w-full"readonly />
              </div>

              <div className="block col-span-5 text-left">
                <input placeholder="description"
                  name="description"
                  value={input.description}
                  className="block rounded-lg px-3 border text-gray-300 border-gray-300 text-base py-2
                focus:outline-none w-full"readonly />
              </div>

              <div className="block col-span-2 text-left">
                <input placeholder="max count"
                  name="maxCount"
                  value={input.maxCount}
                  type="number" min={1}
                  className="block rounded-lg px-3 border text-gray-300 border-gray-300 text-base py-2
                focus:outline-none w-full"readonly />
              </div>

              <button onClick={() => toggleOptionRemoval(index)}>Undo</button>
            </div>
          )
        } else {
          return (
            <div key={index} className="grid grid-cols-12 mt-1 gap-x-3">
              <div className="block col-span-4 text-left">
                <input placeholder="option name"
                  name="name"
                  value={input.name}
                  onChange={event => handleOptionsChange(index, event)}
                  className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
                focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              </div>

              <div className="block col-span-5 text-left">
                <input placeholder="description"
                  name="description"
                  value={input.description}
                  onChange={event => handleOptionsChange(index, event)}
                  className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
                focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              </div>

              <div className="block col-span-2 text-left">
                <input placeholder="max count"
                  name="maxCount"
                  value={input.maxCount}
                  onChange={event => handleOptionsChange(index, event)}
                  type="number" min={1}
                  className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
                focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              </div>

              <button onClick={() => toggleOptionRemoval(index)}>Remove</button>
            </div>
          )
        }
      })}

      {/* Additional options */}
      <div className="block text-left mt-4">
        <label className="text-s">Add new options</label>
      </div>

      {newOptionsInputFields.map((input, index) => {
        return (
          <div key={index} className="grid grid-cols-12 mt-1 gap-x-3">
            <div className="block col-span-4 text-left">
              <input placeholder="option name"
                name="name"
                value={input.name}
                onChange={event => handleNewOptionsChange(index, event)}
                className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
            </div>

            <div className="block col-span-5 text-left">
              <input placeholder="description"
                name="description"
                value={input.description}
                onChange={event => handleNewOptionsChange(index, event)}
                className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
            </div>

            <div className="block col-span-2 text-left">
              <input placeholder="max count"
                name="maxCount"
                value={input.maxCount}
                onChange={event => handleNewOptionsChange(index, event)}
                type="number" min={1}
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
          onChange={(event) => { setTags(event.target.value) }} value={tags}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
        focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
      </div>

      <div className="block text-left mt-4">
        <label className="text-xs">fishing equpiment</label>
        <input autoComplete="off" placeholder="fishing equipment separated by comma"
          onChange={(event) => { setFishingEquipment(event.target.value) }} value={fishingEquipment}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
  focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
      </div>

      <div className="block text-left mt-4">
        <label className="text-xs">navigational equpiment</label>
        <input autoComplete="off" placeholder="navigational equipment separated by comma"
          onChange={(event) => { setNavigationalEquipment(event.target.value) }} value={navigationalEquipment}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
  focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
      </div>


      {/* Boat specials */}
      <div className="grid grid-cols-3 mt-2 gap-x-3">
        <div className="block col-span-1 text-left">
          <label className="text-xs">capacity</label>
          <input autoComplete="off" onChange={(event) => { setCapacity(event.target.value) }} type="number" min={1} value={capacity}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">type</label>
          <input autoComplete="off" onChange={(event) => { setBoatType(event.target.value) }} value={boatType}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">length</label>
          <input autoComplete="off" onChange={(event) => { setBoatLength(event.target.value) }} value={boatLength}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
            focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">engine number</label>
          <input autoComplete="off" onChange={(event) => { setEngineNumber(event.target.value) }} value={engineNumber}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
            focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">engine power</label>
          <input autoComplete="off" onChange={(event) => { setEnginePower(event.target.value) }} value={enginePower}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
            focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">speed</label>
          <input autoComplete="off" onChange={(event) => { setBoatSpeed(event.target.value) }} value={boatSpeed}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
            focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>
      </div>

      {/* Pricing */}
      <h2 className="flex text-xl text-left text-gray-800 font-sans mt-12">
        <Icon className="mr-2" icon="tabler:coin" inline={true} fontSize={30} />
        <span>Pricing</span>
      </h2>

      <div className="grid grid-cols-2 mt-1 gap-x-3">
        <div className="block col-span-1 text-left">
          <label className="text-xs">currency</label>
          <input placeholder="e.g. EUR, USD, RSD"
            value={currency}
            onChange={(event) => { setCurrency(event.target.value) }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">cancellation fee</label>
          <input placeholder="cancellation fee"
            value={cancellationFee}
            onChange={(event) => { setCancellationFee(event.target.value) }}
            type="number"
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>
      </div>

      <div className="block col-span-2 text-left">
        <label className="text-xs">pricing description</label>
        <textarea placeholder="additional info about prices"
          value={pricingDescription}
          onChange={(event) => { setPricingDescription(event.target.value) }}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"
          rows="3" />
      </div>

      <div className="block text-left mt-4">
        <label className="text-s">Prices list</label>
      </div>

      {pricesInputFields.map((input, index) => {
        if (input.delete === true) {
          return (
            <div key={index} className="grid grid-cols-12 mt-1 gap-x-3">
              <div className="block col-span-4 text-left">
                <input placeholder="price"
                  name="value"
                  value={input.value}
                  onChange={event => handlePricesChange(index, event)}
                  className="block rounded-lg px-3 border text-gray-300 border-gray-300 text-base py-2
                focus:outline-none w-full"readonly />
              </div>

              <div className="block col-span-4 text-left">
                <input placeholder="days required"
                  name="minDays"
                  value={input.minDays}
                  onChange={event => handlePricesChange(index, event)}
                  className="block rounded-lg px-3 border text-gray-300 border-gray-300 text-base py-2
                focus:outline-none w-full"readonly />
              </div>

              <button className="block col-span-1" onClick={() => togglePriceRemoval(index)}>Undo</button>
            </div>
          )
        } else {
          return (
            <div key={index} className="grid grid-cols-12 mt-1 gap-x-3">
              <div className="block col-span-4 text-left">
                <input placeholder="price"
                  name="value"
                  value={input.value}
                  onChange={event => handlePricesChange(index, event)}
                  type="number"
                  className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
                focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              </div>

              <div className="block col-span-4 text-left">
                <input placeholder="days required"
                  name="minDays"
                  value={input.minDays}
                  onChange={event => handlePricesChange(index, event)}
                  type="number"
                  className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
                focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              </div>

              <button className="block col-span-1" onClick={() => togglePriceRemoval(index)}>Remove</button>
            </div>
          )
        }
      })}

      <div className="block text-left mt-4">
        <label className="text-s">Add new prices</label>
      </div>

      {newPricesInputFields.map((input, index) => {
        return (
          <div key={index} className="grid grid-cols-12 mt-1 gap-x-3">
            <div className="block col-span-4 text-left">
              <input placeholder="price"
                name="value"
                value={input.value}
                onChange={event => handleNewPricesChange(index, event)}
                type="number"
                className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
            </div>

            <div className="block col-span-4 text-left">
              <input placeholder="days required"
                name="minDays"
                value={input.minDays}
                onChange={event => handleNewPricesChange(index, event)}
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

      {/* Check in/out */}
      <h2 className="flex text-xl text-left text-gray-800 font-sans mt-12">
        <Icon className="mr-2" icon="tabler:clock" inline={true} fontSize={30} />
        <span>Check in and check out</span>
      </h2>
      
      <div className="grid grid-cols-2 mt-1 gap-x-3">
        <div className="block col-span-1 text-left">
          <label className="text-xs">check in</label>
          <input
            onChange={(event) => { setCheckInTime(event.target.value) }}
            type="time" value={checkInTime}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
    focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block col-span-1 text-left">
          <label className="text-xs">check out</label>
          <input
            onChange={(event) => { setCheckOutTime(event.target.value) }}
            type="time" value={checkOutTime}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
    focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>
      </div>

      {/* confirm button */}
      <div className="grid grid-cols-1 lg:grid-cols-3 sm:grid-cols-2 md:gap-x-3 mt-4">
        <div className="flex flex-col justify-end lg:col-start-3 sm:col-start-2 text-left w-full">
          <button className="bg-teal-600 hover:bg-teal-700 active:bg-teal-800 w-full drop-shadow-md
text-white rounded-lg py-2.5 lg:py-2 text-sm lg:text-base mb-1.5 mt-3 md:mt-0"
            onClick={() => { updateAd() }}>
            Save changes
          </button>
        </div>

      </div>

      {/* Availability */}
      <h2 className="flex text-xl text-left text-gray-800 font-sans mt-6 pt-6 border-t border-gray-200">
        <Icon className="mr-2" icon="tabler:calendar" inline={true} fontSize={30} />
        <span>Availability</span>
      </h2>
      
      <div className="grid grid-cols-1 sm:grid-cols-2 mt-1 gap-x-3">
        <div className="col-1 text-left">
          <label className="text-xs">available after</label>
          <div className="flex gap-x-3">
            <input value={availableAfter} type="date"
              onChange={(event) => { setAvailableAfter(event.target.value) }}
              className="rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
        focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        <button className="rounded-lg border border-gray-300 px-3" onClick={() => { setAvailableAfter("") }}>
          <Icon icon="tabler:rotate-clockwise" vFlip={true} fontSize={20} />
        </button>
          </div>
        </div>
        <div className="col-span-1 text-left">
          <label className="text-xs">available until</label>
          <div className="flex gap-x-3">
            <input
              value={availableUntil}
              onChange={(event) => { setAvailableUntil(event.target.value) }}
              type="date"
              className="rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
            <button className="rounded-lg border border-gray-300 px-3" onClick={() => { setAvailableUntil("") }}>
              <Icon icon="tabler:rotate-clockwise" vFlip={true} fontSize={20} />
            </button>
          </div>
        </div>
      </div>

      {/* confirm availability button */}
      <div className="grid grid-cols-1 lg:grid-cols-3 sm:grid-cols-2 md:gap-x-3 mt-4">
        <div className="flex flex-col justify-end lg:col-start-3 sm:col-start-2 text-left w-full">
          <button className="bg-teal-600 hover:bg-teal-700 active:bg-teal-800 w-full drop-shadow-md
text-white rounded-lg py-2.5 lg:py-2 text-sm lg:text-base mb-1.5 mt-3 md:mt-0"
            onClick={() => { updateAvailability() }}>
            Change availability
          </button>
        </div>

      </div>

      {/* delete button */}
      <h2 className="flex text-xl text-left text-gray-800 font-sans mt-6 pt-6 border-t border-gray-200">
        <Icon className="mr-2" icon="tabler:trash" inline={true} fontSize={30} />
        <span>Delete</span>
      </h2>

      <div className="grid grid-cols-1 justify-end lg:grid-cols-3 sm:grid-cols-2 md:gap-x-3 mt-4">
        <div className="flex flex-col lg:col-start-3 sm:col-start-2 text-left w-full">
          <button className="bg-red-600 hover:bg-red-700 active:bg-red-800 w-full drop-shadow-md
text-white rounded-lg py-2.5 lg:py-2 text-sm lg:text-base mb-1.5 mt-3 md:mt-0"
            onClick={() => { setShowModal(true) }}>
            Delete
          </button>
        </div>
      </div>

      {showModal && <DeletionModal closeFunction={() => hide(false)}
        deleteFunction={() => deleteAdvertisement()}
        text={`Are you sure you want to permanently delete ${data.title}? This action cannot be undone.`}
      />}

    </div>
  );
}

export default BoatInfoEditor;