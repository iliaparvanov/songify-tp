module ExceptionHandler
  extend ActiveSupport::Concern

  # Define custom error subclasses - rescue catches `StandardErrors`
  class AuthenticationError < StandardError; end
  class MissingToken < StandardError; end
  class InvalidToken < StandardError; end
  class UserExists < StandardError; end

  included do
    # Define custom handlers
    rescue_from ActiveRecord::RecordInvalid, with: :four_twenty_two
    rescue_from ExceptionHandler::AuthenticationError, with: :unauthorized_request
    rescue_from ExceptionHandler::MissingToken, with: :unauthorized_request
    rescue_from ExceptionHandler::InvalidToken, with: :unauthorized_request
    rescue_from ExceptionHandler::UserExists, with: :bad_request
    rescue_from ActiveRecord::RecordNotFound, with: :not_found
  end

  private

  def four_twenty_two(e)
    json_response({ message: e.message }, 422)
  end

  def bad_request(e)
    json_response({message: "User with that email exists"}, 409)
  end

  def unauthorized_request(e)
    json_response({message: e.message}, 401)
  end

  def not_found(e) 
    json_response({message: "Record not found"}, 404)
  end
end