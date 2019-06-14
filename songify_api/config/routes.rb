Rails.application.routes.draw do
  resources :artists do
    resources :albums
  end
end
