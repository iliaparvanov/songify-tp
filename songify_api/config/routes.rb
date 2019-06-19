Rails.application.routes.draw do
  resources :artists do
    resources :albums
  end

  resources :songs do 
  end
  
  post 'auth/login', to: 'authentication#authenticate'
  post 'signup', to: 'users#create'
end
