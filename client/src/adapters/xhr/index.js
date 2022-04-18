import Axios from "axios";

function returnAxiosInstance() {
  return Axios.create({});
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