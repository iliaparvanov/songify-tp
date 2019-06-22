# app/controllers/authentication_controller.rb
class AuthenticationController < ApplicationController
  # return auth token once user is authenticated
  skip_before_action :authorize_request, only: :authenticate
  def authenticate
    p current_user
    auth_token = AuthenticateUser.new(auth_params[:email], auth_params[:password]).call
    p current_user
    json_response(auth_token: auth_token)
  end

  private

  def auth_params
    params.permit(:email, :password)
  end
end