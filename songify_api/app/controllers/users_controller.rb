class UsersController < ApplicationController
  skip_before_action :authorize_request, only: :create
  def create
    if "secret_key_that_no_one_knows" == params["key_for_signup"]

      if User.find_by(email: params["email"]).nil?
        user = User.create!(user_params)
        auth_token = AuthenticateUser.new(user.email, user.password).call
        response = { message: Message.account_created, auth_token: auth_token }
        json_response(response, :created)
      else
        response = { message: "User with that email exists"}
        raise ExceptionHandler::UserExists
        json_response(response)
      end
    else
      response = { message: "Invalid key"}
      json_response(response)
    end

  end

  private

  def user_params
    params.permit(
      :name,
      :email,
      :password,
      :password_confirmation
    )
  end
end