import Axios from "axios";
import { getToken } from "../../contexts";

function returnAxiosInstance() {
  let instance = Axios.create({});
  if (getToken() !== undefined)
    instance.defaults.headers.common["Authorization"] = `Bearer ${getToken()}`;

  return instance;
}

export function get(url){
  const axios = returnAxiosInstance();
  return axios.get(url);
}

export function post(url, requestData){
  const axios = returnAxiosInstance();
  return axios.post(url, requestData);
}

export function put(url, requestData){
  const axios = returnAxiosInstance();
  return axios.put(url, requestData);
}

export function patch(url, requestData){
  const axios = returnAxiosInstance();
  return axios.patch(url, requestData);
}

export function del(url, requestData){
  const axios = returnAxiosInstance();
  return axios.delete(url, {data: requestData});
}